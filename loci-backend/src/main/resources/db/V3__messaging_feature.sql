    create table authority (
        name varchar(50) not null,
        primary key (name)
    );

    create table contact (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        blocked_by bigint,
        contact_user_id bigint not null,
        user_id bigint not null,
        primary key (id)
    );

    create table contact_request (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        receiver_user_id bigint not null,
        request_user_id bigint not null,
        primary key (id)
    );

    create table conversation_participant (
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

    create table conversations (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        deleted boolean not null,
        group_name varchar(255),
        group_profile_picture varchar(500),
        last_message_id bigint,
        updated_at timestamp(6) with time zone,
        creator_id bigint not null,
        primary key (id)
    );

    create table group (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        group_name varchar(255) not null,
        group_profile_picture varchar(500),
        last_active timestamp(6) with time zone,
        conversation_id bigint not null,
        primary key (id)
    );

    create table message (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT not null,
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

    create table notification (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT not null,
        message_thumbnail varchar(500),
        read_at timestamp(6) with time zone,
        user_id bigint not null,
        primary key (id)
    );

    create table user_ (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        bio varchar(255),
        email varchar(255),
        firstname varchar(255) not null,
        friend_request_setting smallint check (friend_request_setting between 0 and 2),
        last_active timestamp(6) with time zone,
        last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')),
        lastname varchar(255) not null,
        profile_picture varchar(255),
        profile_visibility boolean,
        public_id uuid,
        username varchar(255),
        primary key (id)
    );

    create table user_authority (
        user_id bigint not null,
        authority_name varchar(50) not null,
        primary key (user_id, authority_name)
    );

    alter table if exists group
       drop constraint if exists UK6hmawf8v0dik6sahkye7xx05a;

    alter table if exists group
       add constraint UK6hmawf8v0dik6sahkye7xx05a unique (conversation_id);

    create sequence contact_request_sequence start with 1 increment by 1;

    create sequence contact_sequence start with 1 increment by 1;

    create sequence conversation_participant_sequence start with 1 increment by 1;

    create sequence conversation_sequence start with 1 increment by 1;

    create sequence group_sequence start with 1 increment by 1;

    create sequence message_sequence start with 1 increment by 1;

    create sequence notification_sequence start with 1 increment by 1;

    create sequence user_sequence start with 1 increment by 1;

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

    alter table if exists group
       add constraint FKx9726ylvuvb0wsdarel7o4l5
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

    alter table if exists user_authority
       add constraint FK6ktglpl5mjosa283rvken2py5
       foreign key (authority_name)
       references authority;

    alter table if exists user_authority
       add constraint FKio2xcw9ogcqbasp25n5vttxrf
       foreign key (user_id)
       references user_;
