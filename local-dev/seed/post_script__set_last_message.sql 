UPDATE conversation SET last_message_id = ( SELECT id FROM message WHERE conversation_id  = conversation.id  ORDER BY  message.delivered_at  DESC LIMIT  1) ;
update  conversation_participant set last_read_message_id = ( SELECT id FROM message WHERE conversation_id  = conversation_participant.conversation_id ORDER BY  message.delivered_at  DESC LIMIT  1) ;
