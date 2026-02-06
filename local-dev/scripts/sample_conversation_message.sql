-- 5. Bảng conversation (phụ thuộc user_ cho creator_id)
-- Chú ý: last_message_id sẽ update sau khi insert message
INSERT INTO conversation (
    id, public_id, conversation_type, creator_id,
    deleted, created_date, last_modified_date
) VALUES (
    nextval('conversation_sequence'),
    gen_random_uuid(),
    'ONE_TO_ONE',
    1, -- creator_id = user 1
    false,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO conversation (
    id, public_id, conversation_type, creator_id,
    deleted, created_date, last_modified_date
) VALUES (
    nextval('conversation_sequence'),
    gen_random_uuid(),
    'GROUP',
    1, -- creator_id = user 1
    false,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 6. Bảng message (phụ thuộc conversation và user_)
INSERT INTO message (
    id, public_id, content, type, status,
    conversation_id, sender_id, sent_at,
    delivered_at, read_at, deleted,
    media_name, media_url, reply_to_message_id,
    created_date, last_modified_date
) VALUES (
    nextval('message_sequence'),
    gen_random_uuid(),
    'Hello, how are you?',
    'TEXT',
    'SEEN',
    1, -- conversation_id
    1, -- sender_id
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    false,
    null,
    null,
    null,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO message (
    id, public_id, content, type, status,
    conversation_id, sender_id, sent_at,
    delivered_at, read_at, deleted,
    media_name, media_url, reply_to_message_id,
    created_date, last_modified_date
) VALUES (
    nextval('message_sequence'),
    gen_random_uuid(),
    'I am good, thanks!',
    'TEXT',
    'DELIVERED',
    1, -- conversation_id
    2, -- sender_id (reply từ user 2)
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    null, -- chưa read
    false,
    null,
    null,
    1, -- reply_to_message_id = message 1
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- 7. Update conversation để set last_message_id (optional, nếu cần)
UPDATE conversation
SET last_message_id = 2,
    last_message_sent = CURRENT_TIMESTAMP
WHERE id = 1;
