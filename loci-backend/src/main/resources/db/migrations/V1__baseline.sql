    create sequence contact_request_sequence start with 1 increment by 1;

    create sequence contact_sequence start with 1 increment by 1;

    create sequence conversation_participant_sequence start with 1 increment by 1;

    create sequence conversation_sequence start with 1 increment by 1;

    create sequence group_sequence start with 1 increment by 1;

    create sequence message_sequence start with 1 increment by 1;

    create sequence notification_sequence start with 1 increment by 1;

    create sequence user_sequence start with 1 increment by 1;

    create table authority (
        name varchar(50) not null,
        primary key (name)
    );

    create table contact (
        blocked_by bigint,
        contact_user_id bigint not null,
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        user_id bigint not null,
        primary key (id)
    );

    create table contact_request (
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        receiver_user_id bigint not null,
        request_user_id bigint not null,
        public_id uuid unique,
        status varchar(255) check (status in ('PENDING','ACCEPTED','DECLINED','CANCELED')),
        primary key (id)
    );

    create table conversation (
        deleted boolean not null,
        created_date timestamp(6) with time zone,
        creator_id bigint not null,
        id bigint not null,
        last_message_id bigint,
        last_message_sent timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        public_id uuid unique,
        conversation_type varchar(255) not null check (conversation_type in ('ONE_TO_ONE','GROUP')),
        primary key (id)
    );

    create table conversation_participant (
        conversation_id bigint not null,
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        last_read_message_id bigint,
        user_id bigint not null,
        role varchar(20) not null check (role in ('ADMIN','MEMBER')),
        primary key (id)
    );

    create table group_ (
        conversation_id bigint not null unique,
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_active timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        public_id uuid not null unique,
        group_profile_picture varchar(500),
        group_name varchar(255) not null,
        primary key (id)
    );

    create table message (
        deleted boolean not null,
        conversation_id bigint not null,
        created_date timestamp(6) with time zone,
        delivered_at timestamp(6) with time zone,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        read_at timestamp(6) with time zone,
        reply_to_message_id bigint,
        sender_id bigint not null,
        sent_at timestamp(6) with time zone,
        public_id uuid unique,
        status varchar(20) not null check (status in ('PREPARE','SENT','DELIVERED','SEEN')),
        type varchar(20) not null check (type in ('TEXT','FILE','IMAGE','VIDEO')),
        media_name varchar(100),
        media_url varchar(500),
        content TEXT,
        primary key (id)
    );

    create table notification (
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_modified_date timestamp(6) with time zone,
        read_at timestamp(6) with time zone,
        user_id bigint not null,
        public_id uuid unique,
        message_thumbnail varchar(500),
        content TEXT not null,
        primary key (id)
    );

    create table user_ (
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_active timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        public_id uuid unique,
        bio varchar(255),
        email varchar(255) unique,
        firstname varchar(255),
        lastname varchar(255),
        profile_picture varchar(255),
        username varchar(255) unique,
        primary key (id)
    );

    create table user_authority (
        user_id bigint not null,
        authority_name varchar(50) not null,
        primary key (user_id, authority_name)
    );

    create table user_setting (
        profile_visibility boolean,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        user_id bigint not null,
        friend_request_setting varchar(255) check (friend_request_setting in ('EVERYONE','FRIENDS_OF_FRIENDS','NOBODY')),
        last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')),
        primary key (user_id)
    );

    alter table if exists contact
       add constraint FKcgt5xpclo1jvlvy763u6m3w26
       foreign key (blocked_by)
       references user_;

    alter table if exists contact
       add constraint FK7rlqroy8v218wadpf5do3el2e
       foreign key (contact_user_id)
       references user_;

    alter table if exists contact
       add constraint FK56fuy74fokpcs1mamr88g3jbw
       foreign key (user_id)
       references user_;

    alter table if exists contact_request
       add constraint FKn2g1ehilahjakmnnbncklfbog
       foreign key (receiver_user_id)
       references user_;

    alter table if exists contact_request
       add constraint FKt6jacf36093nt67xmxnuunyau
       foreign key (request_user_id)
       references user_;

    alter table if exists conversation
       add constraint FKk4ff054uhh47bajpq2ioterxo
       foreign key (creator_id)
       references user_;

    alter table if exists conversation
       add constraint FKsm3966podppo987o2etdjci1r
       foreign key (last_message_id)
       references message;

    alter table if exists conversation_participant
       add constraint FK93dv599s56uqs8xslhdp3arya
       foreign key (conversation_id)
       references conversation;

    alter table if exists conversation_participant
       add constraint FKhf6gkkuops56saeu2gqrj9pp9
       foreign key (user_id)
       references user_;

    alter table if exists group_
       add constraint FK1q8ojqw7oymq1axfw8ovbul5x
       foreign key (conversation_id)
       references conversation;

    alter table if exists message
       add constraint FK6yskk3hxw5sklwgi25y6d5u1l
       foreign key (conversation_id)
       references conversation;

    alter table if exists message
       add constraint FKm83oxlnewy7ev6dc1pxjwr4rn
       foreign key (reply_to_message_id)
       references message;

    alter table if exists message
       add constraint FK2c48vm73iafadi7iqjj6wp2g
       foreign key (sender_id)
       references user_;

    alter table if exists notification
       add constraint FKg9wcclio3v5xftqnc4q7lr7hd
       foreign key (user_id)
       references user_;

    alter table if exists user_authority
       add constraint FK6ktglpl5mjosa283rvken2py5
       foreign key (authority_name)
       references authority;

    alter table if exists user_authority
       add constraint FKio2xcw9ogcqbasp25n5vttxrf
       foreign key (user_id)
       references user_;
