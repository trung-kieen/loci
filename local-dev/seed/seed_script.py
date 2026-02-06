import psycopg2
from psycopg2.extras import execute_values
from faker import Faker
import random
from datetime import datetime, timedelta
from typing import List, Tuple, Set

fake = Faker('en_US')
Faker.seed(42)
random.seed(42)
import os
from dotenv import load_dotenv
load_dotenv()

DB_CONFIG = {
    'host': os.getenv('DB_HOST', 'localhost'),
    'database': os.getenv('DB_DATABASE', 'locidb'),
    'user': os.getenv('DB_USER', 'root'),
    'password': os.getenv('DB_PASSWORD', ''),
    'port': os.getenv('DB_PORT', '5432')
}
required_vars = ['DB_HOST', 'DB_DATABASE', 'DB_USER', 'DB_PASSWORD']
missing = [var for var in required_vars if not os.getenv(var)]
if missing:
    raise EnvironmentError(f"Missing required environment variables: {', '.join(missing)}")

CHECK_CONSTRAINTS = {
    'status': ['PENDING', 'ACCEPTED', 'DECLINED', 'CANCELED'],
    'conversation_type': ['ONE_TO_ONE', 'GROUP'],
    'role': ['ADMIN', 'MEMBER'],
    'message_status': ['PREPARE', 'SENT', 'DELIVERED', 'SEEN'],
    'message_type': ['TEXT', 'FILE', 'IMAGE', 'VIDEO'],
    'friend_request_setting': ['EVERYONE', 'FRIENDS_OF_FRIENDS', 'NOBODY'],
    'last_seen_setting': ['EVERYONE', 'CONTACT_ONLY', 'NOBODY']
}

class DatabaseSeeder:
    def __init__(self):
        self.conn = None
        self.cursor = None
        self.user_ids: List[int] = []
        self.conversation_ids: List[int] = []

    def connect(self):
        """Establish database connection"""
        self.conn = psycopg2.connect(**DB_CONFIG)
        self.cursor = self.conn.cursor()
        print(f"Connected to {DB_CONFIG['database']} database")

    def disconnect(self):
        """Close database connection"""
        if self.cursor:
            self.cursor.close()
        if self.conn:
            self.conn.close()
        print("Database connection closed")

    def reset_sequences(self):
        """Reset all sequences to start fresh"""
        sequences = [
            'user_sequence', 'conversation_sequence', 'message_sequence',
            'contact_sequence', 'contact_request_sequence',
            'conversation_participant_sequence', 'group_sequence', 'notification_sequence'
        ]
        for seq in sequences:
            self.cursor.execute(f"ALTER SEQUENCE {seq} RESTART WITH 1")
        self.conn.commit()
        print("Reset all sequences")

    def generate_timestamp(self, days_back: int = 365) -> datetime:
        """Generate timezone-aware timestamp within last year"""
        end = datetime.now()
        start = end - timedelta(days=days_back)
        return fake.date_time_between(start_date=start, end_date=end, tzinfo=None)

    def seed_authorities(self):
        """Insert base authority roles"""
        authorities = [('ROLE_user',), ('ROLE_admin',), ('ROLE_moderator',)]
        execute_values(
            self.cursor,
            "INSERT INTO authority (name) VALUES %s ON CONFLICT (name) DO NOTHING",
            authorities
        )
        self.conn.commit()
        print(f"Inserted {len(authorities)} authorities")

    def seed_users(self, count: int = 40):
        """Generate users with unique constraints handling"""
        used_usernames: Set[str] = set()
        used_emails: Set[str] = set()
        users_data = []

        for i in range(count):
            # Ensure uniqueness
            while True:
                username = fake.user_name() + str(random.randint(1, 999))
                email = fake.email()
                if username not in used_usernames and email not in used_emails:
                    used_usernames.add(username)
                    used_emails.add(email)
                    break

            firstname = fake.first_name()
            lastname = fake.last_name()
            bio = fake.text(max_nb_chars=100)
            profile_pic = f"https://api.dicebear.com/7.x/notionists/svg?scale=200&seed={random.randint(1, 10000)}"
            # profile_pic = f"https://i.pravatar.cc/150?u={random.randint(1, 10000)}"
            created_date = self.generate_timestamp()
            last_active = fake.date_time_between(start_date=created_date, end_date='now')

            # Format: all fields that use %s placeholder
            users_data.append((
                username, email, firstname, lastname,
                bio, profile_pic, created_date, created_date, last_active
            ))

        # FIX: Use INSERT with SELECT nextval/gen_random_uuid instead of inline in VALUES
        # execute_values doesn't support %s inside VALUES, so we do row-by-row for users to get IDs
        for data in users_data:
            self.cursor.execute("""
                INSERT INTO user_ (
                    id, public_id, username, email, firstname, lastname,
                    bio, profile_picture, created_date, last_modified_date, last_active
                ) VALUES (
                    nextval('user_sequence'), gen_random_uuid(), %s, %s, %s, %s,
                    %s, %s, %s, %s, %s
                ) RETURNING id
            """, data)
            user_id = self.cursor.fetchone()[0]
            self.user_ids.append(user_id)

        # Batch insert authorities and settings
        user_authorities = [(uid, 'ROLE_user') for uid in self.user_ids]
        user_settings = []
        for uid in self.user_ids:
            user_settings.append((
                uid, True,
                random.choice(CHECK_CONSTRAINTS['friend_request_setting']),
                random.choice(CHECK_CONSTRAINTS['last_seen_setting']),
                datetime.now(), datetime.now()
            ))

        execute_values(
            self.cursor,
            "INSERT INTO user_authority (user_id, authority_name) VALUES %s",
            user_authorities
        )

        execute_values(
            self.cursor,
            """
            INSERT INTO user_setting (
                user_id, profile_visibility, friend_request_setting,
                last_seen_setting, created_date, last_modified_date
            ) VALUES %s
            """,
            user_settings
        )

        self.conn.commit()
        print(f"Inserted {count} users with ROLE_user authority and settings")

    def seed_contacts(self):
        """Create mutual contacts (undirected graph - no duplicates)"""
        contacts_data = []
        contact_pairs: Set[Tuple[int, int]] = set()

        # Create random connections, ~3-8 contacts per user
        for user_id in self.user_ids:
            num_contacts = random.randint(3, min(8, len(self.user_ids) - 1))
            potential_contacts = [u for u in self.user_ids if u != user_id]
            selected = random.sample(potential_contacts, min(num_contacts, len(potential_contacts)))

            for contact_id in selected:
                # Ensure mutual and no duplicate (store sorted tuple)
                pair = tuple(sorted([user_id, contact_id]))
                if pair not in contact_pairs:
                    contact_pairs.add(pair)
                    created_date = self.generate_timestamp()
                    # Randomly assign one as blocker (nullable)
                    blocked_by = random.choice([None, None, None, user_id, contact_id])
                    contacts_data.append((
                        blocked_by, pair[1], created_date, created_date, pair[0]
                    ))

        # FIX: Use execute_values with template that includes nextval
        execute_values(
            self.cursor,
            """
            INSERT INTO contact (
                id, blocked_by, contact_user_id, created_date,
                last_modified_date, user_id
            ) VALUES %s
            """,
            contacts_data,
            template="(nextval('contact_sequence'), %s, %s, %s, %s, %s)"
        )
        self.conn.commit()
        print(f"Inserted {len(contacts_data)} mutual contacts")
        return contact_pairs

    def seed_conversations(self, contact_pairs: Set[Tuple[int, int]]):
        """Create ONE_TO_ONE conversations between contacts"""
        participants_data = []

        for user_a, user_b in contact_pairs:
            created_date = self.generate_timestamp()
            creator_id = random.choice([user_a, user_b])

            # Insert conversation individually to get ID
            self.cursor.execute("""
                INSERT INTO conversation (
                    id, public_id, conversation_type, creator_id,
                    deleted, created_date, last_modified_date
                ) VALUES (
                    nextval('conversation_sequence'), gen_random_uuid(),
                    'ONE_TO_ONE', %s, false, %s, %s
                ) RETURNING id
            """, (creator_id, created_date, created_date))

            conv_id = self.cursor.fetchone()[0]
            self.conversation_ids.append(conv_id)

            # Add participants
            for user_id in [user_a, user_b]:
                participants_data.append((
                    conv_id, user_id, 'MEMBER', None, created_date, created_date
                ))

        # Batch insert participants using template for nextval
        execute_values(
            self.cursor,
            """
            INSERT INTO conversation_participant (
                id, conversation_id, user_id, role,
                last_read_message_id, created_date, last_modified_date
            ) VALUES %s
            """,
            participants_data,
            template="(nextval('conversation_participant_sequence'), %s, %s, %s, %s, %s, %s)"
        )

        self.conn.commit()
        print(f"Inserted {len(self.conversation_ids)} ONE_TO_ONE conversations")

    def seed_messages(self):
        """Generate messages with realistic conversation flows"""
        all_messages = []

        for conv_id in self.conversation_ids:
            # Determine message count: avg 15-30, some with 50+
            if random.random() < 0.2:  # 20% chance of long conversation
                msg_count = random.randint(45, 80)
            else:
                msg_count = random.randint(15, 30)

            # Get participants
            self.cursor.execute(
                "SELECT user_id FROM conversation_participant WHERE conversation_id = %s",
                (conv_id,)
            )
            participants = [row[0] for row in self.cursor.fetchall()]

            # Generate message timeline
            current_time = self.generate_timestamp()
            conversation_messages = []

            for i in range(msg_count):
                sender = random.choice(participants)
                msg_type = random.choices(
                    CHECK_CONSTRAINTS['message_type'],
                    weights=[70, 10, 15, 5],  # Mostly text
                    k=1
                )[0]

                # Generate content based on type
                if msg_type == 'TEXT':
                    content = fake.sentence(nb_words=random.randint(3, 20))
                else:
                    content = f"{msg_type.lower()}_content_{random.randint(1000, 9999)}"

                # Timestamps with realistic intervals (1-60 mins apart)
                current_time += timedelta(minutes=random.randint(1, 60))
                sent_at = current_time

                # Message status progression
                if i == msg_count - 1:  # Latest message
                    status = random.choice(['SENT', 'DELIVERED'])
                    delivered_at = sent_at + timedelta(seconds=random.randint(1, 60)) if status != 'SENT' else None
                    read_at = None
                else:
                    status = 'SEEN'
                    delivered_at = sent_at + timedelta(seconds=random.randint(1, 60))
                    read_at = delivered_at + timedelta(seconds=random.randint(1, 300))

                media_name = f"media_{random.randint(1000, 9999)}" if msg_type != 'TEXT' else None
                media_url = f"https://cdn.example.com/{random.randint(1000, 9999)}" if msg_type != 'TEXT' else None

                # Simple reply logic (10% chance to reply to previous message)
                reply_to = None

                conversation_messages.append((
                    content, msg_type, status, conv_id, sender, sent_at,
                    delivered_at, read_at, False, media_name, media_url, reply_to,
                    sent_at, sent_at
                ))

            all_messages.extend(conversation_messages)

        # Insert all messages with template for nextval and gen_random_uuid
        execute_values(
            self.cursor,
            """
            INSERT INTO message (
                id, public_id, content, type, status,
                conversation_id, sender_id, sent_at, delivered_at, read_at,
                deleted, media_name, media_url, reply_to_message_id,
                created_date, last_modified_date
            ) VALUES %s
            RETURNING id, conversation_id, sent_at
            """,
            all_messages,
            template="(nextval('message_sequence'), gen_random_uuid(), %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
        )

        returned = self.cursor.fetchall()

        # Update conversations with last_message_id and last_message_sent
        conv_updates = {}
        for msg_id, conv_id, sent_at in returned:
            if conv_id not in conv_updates or sent_at > conv_updates[conv_id][1]:
                conv_updates[conv_id] = (msg_id, sent_at)

        # Update each conversation individually (can't use execute_values for UPDATE with FROM VALUES easily)
        for conv_id, (msg_id, sent_at) in conv_updates.items():
            self.cursor.execute("""
                UPDATE conversation
                SET last_message_id = %s, last_message_sent = %s
                WHERE id = %s
            """, (msg_id, sent_at, conv_id))

        self.conn.commit()
        print(f"Inserted {len(all_messages)} messages across {len(self.conversation_ids)} conversations")
        print("Updated conversation last_message references")

    def run(self):
        """Execute complete seeding process"""
        try:
            self.connect()

            print("\n=== STARTING DATABASE SEED ===\n")

            self.reset_sequences()
            self.seed_authorities()
            self.seed_users(40)
            contact_pairs = self.seed_contacts()
            self.seed_conversations(contact_pairs)
            self.seed_messages()

            print("\n=== SEEDING COMPLETED SUCCESSFULLY ===")
            print(f"Summary:")
            print(f"  - Users: {len(self.user_ids)}")
            print(f"  - Contacts: {len(contact_pairs)} pairs")
            print(f"  - Conversations: {len(self.conversation_ids)}")

        except Exception as e:
            print(f"\nERROR: {e}")
            if self.conn:
                self.conn.rollback()
            raise
        finally:
            self.disconnect()

if __name__ == "__main__":
    seeder = DatabaseSeeder()
    seeder.run()
