
-- Reset the sequence to avoid inconsitence data

SELECT setval('user_sequence', (SELECT MAX(id) FROM user_), true);

SELECT setval('contact_request_sequence', (SELECT MAX(id) FROM contact_request), true);

SELECT setval('contact_sequence', (SELECT MAX(id) FROM contact), true);

SELECT setval('conversation_participant_sequence', (SELECT MAX(id) FROM conversation_participant), true);

SELECT setval('conversation_sequence', (SELECT MAX(id) FROM conversation), true);

SELECT setval('group_sequence', (SELECT MAX(id) FROM group_), true);

SELECT setval('message_sequence', (SELECT MAX(id) FROM message), true);

SELECT setval('notification_sequence', (SELECT MAX(id) FROM notification), true);
