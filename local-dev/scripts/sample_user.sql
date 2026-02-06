-- 1. Bảng authority (không có FK)
INSERT INTO authority (name) VALUES ('ROLE_user');
INSERT INTO authority (name) VALUES ('ROLE_admin');
INSERT INTO authority (name) VALUES ('ROLE_moderator');

-- 2. Bảng user_ (không có FK đến bảng khác ngoài sequence)
-- Sử dụng nextval để lấy ID từ sequence
INSERT INTO user_ (
    id, public_id, username, email, firstname, lastname,
    bio, profile_picture, created_date, last_modified_date, last_active
) VALUES (
    nextval('user_sequence'),
    gen_random_uuid(),
    'john_doe',
    'john@example.com',
    'John',
    'Doe',
    'Software developer',
    'https://example.com/avatars/john.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO user_ (
    id, public_id, username, email, firstname, lastname,
    bio, profile_picture, created_date, last_modified_date, last_active
) VALUES (
    nextval('user_sequence'),
    gen_random_uuid(),
    'jane_smith',
    'jane@example.com',
    'Jane',
    'Smith',
    'Product manager',
    'https://example.com/avatars/jane.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO user_ (
    id, public_id, username, email, firstname, lastname,
    bio, profile_picture, created_date, last_modified_date, last_active
) VALUES (
    nextval('user_sequence'),
    gen_random_uuid(),
    'bob_wilson',
    'bob@example.com',
    'Bob',
    'Wilson',
    'Designer',
    'https://example.com/avatars/bob.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 3. Bảng user_authority (phụ thuộc user_ và authority)
-- Giả sử user ID 1, 2, 3 đã được tạo
INSERT INTO user_authority (user_id, authority_name) VALUES (1, 'ROLE_user');
INSERT INTO user_authority (user_id, authority_name) VALUES (1, 'ROLE_admin');
INSERT INTO user_authority (user_id, authority_name) VALUES (2, 'ROLE_user');
INSERT INTO user_authority (user_id, authority_name) VALUES (3, 'ROLE_user');

-- 4. Bảng user_setting (phụ thuộc user_)
INSERT INTO user_setting (
    user_id, profile_visibility, friend_request_setting,
    last_seen_setting, created_date, last_modified_date
) VALUES (
    1, true, 'EVERYONE', 'EVERYONE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);
INSERT INTO user_setting (
    user_id, profile_visibility, friend_request_setting,
    last_seen_setting, created_date, last_modified_date
) VALUES (
    2, true, 'FRIENDS_OF_FRIENDS', 'CONTACT_ONLY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);
INSERT INTO user_setting (
    user_id, profile_visibility, friend_request_setting,
    last_seen_setting, created_date, last_modified_date
) VALUES (
    3, false, 'NOBODY', 'NOBODY', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
);
