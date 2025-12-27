    alter table if exists message
       drop column reply_to_message_id;

    alter table if exists message
       add column reply_to_message_id bigint;
    alter table if exists message
       add constraint FKm83oxlnewy7ev6dc1pxjwr4rn
       foreign key (reply_to_message_id)
       references message;
