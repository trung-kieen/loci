-- 8. Bảng group_ (phụ thuộc conversation)
INSERT INTO group_ (
    id, public_id, group_name, group_profile_picture,
    conversation_id, created_date, last_modified_date, last_active
) VALUES (
    nextval('group_sequence'),
    gen_random_uuid(),
    'Developers Team',
    'https://example.com/groups/dev-team.jpg',
    2, -- conversation_id = conversation 2 (GROUP type)
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 9. Bảng conversation_participant (phụ thuộc conversation và user_)
INSERT INTO conversation_participant (
    id, conversation_id, user_id, role,
    last_read_message_id, created_date, last_modified_date
) VALUES (
    nextval('conversation_participant_sequence'),
    1, -- conversation_id
    1, -- user_id
    'ADMIN',
    2, -- last_read_message_id
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO conversation_participant (
    id, conversation_id, user_id, role,
    last_read_message_id, created_date, last_modified_date
) VALUES (
    nextval('conversation_participant_sequence'),
    1, -- conversation_id
    2, -- user_id
    'MEMBER',
    2, -- last_read_message_id
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Participant cho group conversation (id=2)
INSERT INTO conversation_participant (
    id, conversation_id, user_id, role,
    last_read_message_id, created_date, last_modified_date
) VALUES (
    nextval('conversation_participant_sequence'),
    2, -- conversation_id (group)
    1, -- user_id (admin)
    'ADMIN',
    null,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO conversation_participant (
    id, conversation_id, user_id, role,
    last_read_message_id, created_date, last_modified_date
) VALUES (
    nextval('conversation_participant_sequence'),
    2, -- conversation_id
    2, -- user_id
    'MEMBER',
    null,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO conversation_participant (
    id, conversation_id, user_id, role,
    last_read_message_id, created_date, last_modified_date
) VALUES (
    nextval('conversation_participant_sequence'),
    2, -- conversation_id
    3, -- user_id
    'MEMBER',
    null,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
