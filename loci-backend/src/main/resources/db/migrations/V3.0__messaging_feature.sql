    create table if not exists user_ (
        profile_visibility boolean,
        created_date timestamp(6) with time zone,
        id bigint not null,
        last_active timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        last_seen timestamp(6) with time zone,
        public_id uuid,
        bio varchar(255),
        email varchar(255),
        firstname varchar(255),
        image_url varchar(255),
        lastname varchar(255), primary key (id)
    );
    create table if not exists contact (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        blocked_by bigint not null,
        contact_user_id bigint not null,
        user_id bigint not null,
        primary key (id)
    );


    create table if not exists contact_request (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        receiver_user_id bigint not null,
        request_user_id bigint not null,
        primary key (id)
    );

    create table if not exists conversation_participant (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        joined_at timestamp(6) with time zone not null,
        last_read_message_id bigint,
        role varchar(20) not null check (role in ('MEMBER','ADMIN')),
        conversation_id bigint not null,
        user_id bigint not null,
        primary key (id)
    );

    create table if not exists conversations (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        creator_id bigint not null,
        deleted boolean not null,
        group_name varchar(255),
        group_profile_picture varchar(500),
        last_message_id bigint,
        updated_at timestamp(6) with time zone,
        primary key (id)
    );

    create table if not exists group_ (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        group_name varchar(255) not null,
        group_profile_picture varchar(500),
        last_active timestamp(6) with time zone,
        conversation_id bigint not null,
        primary key (id)
    );

    create table if not exists message (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT,
        deleted boolean not null,
        delivered_at timestamp(6) with time zone,
        media_url varchar(500),
        read_at timestamp(6) with time zone,
        reply_to_message_id uuid,
        sent_at timestamp(6) with time zone,
        status varchar(20) not null check (status in ('PREPARE','SENT','DELIVERED','SEEN')),
        type varchar(20) not null check (type in ('TEXT','FILE','IMAGE','VIDEO')),
        conversation_id bigint not null,
        sender_id bigint not null,
        primary key (id)
    );

    create table if not exists notification (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT,
        message_thumbnail varchar(500),
        read_at timestamp(6) with time zone,
        user_id bigint not null,
        primary key (id)
    );

    alter table if exists group_
       drop constraint if exists UKgdlqdiqvcgqbe1fspoysdsjpw;

    alter table if exists group_
       add constraint UKgdlqdiqvcgqbe1fspoysdsjpw unique (conversation_id);

    create sequence contact_request_sequence start with 1 increment by 1;

    create sequence contact_sequence start with 1 increment by 1;

    create sequence conversation_participant_sequence start with 1 increment by 1;

    create sequence conversation_sequence start with 1 increment by 1;

    create sequence group_sequence start with 1 increment by 1;

    create sequence message_sequence start with 1 increment by 1;

    create sequence notification_sequence start with 1 increment by 1;

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

    alter table if exists conversation_participant
       add constraint FKj6b2ggxpac5meuh02olopl9d9
       foreign key (conversation_id)
       references conversations;

    alter table if exists conversation_participant
       add constraint FKhf6gkkuops56saeu2gqrj9pp9
       foreign key (user_id)
       references user_;

    alter table if exists conversations
       add constraint FKec5vwoskn10x76g5ks833b8nk
       foreign key (creator_id)
       references user_;

    alter table if exists group_
       add constraint FK1s9ggxi7i7i7gq3869dlyws5e
       foreign key (conversation_id)
       references conversations;

    alter table if exists message
       add constraint FKgn1by50g9ur1g9veuroqmugin
       foreign key (conversation_id)
       references conversations;

    alter table if exists message
       add constraint FK2c48vm73iafadi7iqjj6wp2g
       foreign key (sender_id)
       references user_;

    alter table if exists notification
       add constraint FKg9wcclio3v5xftqnc4q7lr7hd
       foreign key (user_id)
       references user_;
