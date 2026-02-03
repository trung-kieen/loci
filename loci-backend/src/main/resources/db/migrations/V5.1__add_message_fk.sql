alter table if exists conversation_participant
  add constraint fk_last_message
  FOREIGN KEY(last_read_message_id) REFERENCES message(id)
