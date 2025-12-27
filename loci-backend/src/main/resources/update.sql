
    alter table if exists message 
       alter column reply_to_message_id set data type bigint;

    alter table if exists message 
       add constraint FKm83oxlnewy7ev6dc1pxjwr4rn 
       foreign key (reply_to_message_id) 
       references message;
