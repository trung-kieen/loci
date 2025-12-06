    drop table conversations CASCADE;
    create table conversation (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        creator_id bigint not null,
        deleted boolean not null,
        group_name varchar(255),
        group_profile_picture varchar(500),
        last_message_id bigint,
        public_id uuid,
        updated_at timestamp(6) with time zone,
        primary key (id)
    );

    alter table if exists conversation
       drop constraint if exists UKgafcthea67ee76r7f01clcw81;

    alter table if exists conversation
       add constraint UKgafcthea67ee76r7f01clcw81 unique (public_id);

    alter table if exists conversation
       add constraint FKk4ff054uhh47bajpq2ioterxo
       foreign key (creator_id)
       references user_;

    alter table if exists conversation_participant
       add constraint FK93dv599s56uqs8xslhdp3arya
       foreign key (conversation_id)
       references conversation;

    alter table if exists group_
       add constraint FK1q8ojqw7oymq1axfw8ovbul5x
       foreign key (conversation_id)
       references conversation;

    alter table if exists message
       add constraint FK6yskk3hxw5sklwgi25y6d5u1l
       foreign key (conversation_id)
       references conversation;
