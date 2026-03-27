-- Copyright 2026 trung-kieen
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.


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
        public_id uuid,
        receiver_user_id bigint not null,
        request_user_id bigint not null,
        status varchar(255) check (status in ('PENDING','ACCEPTED','DECLINED','CANCELED')),
        primary key (id)
    );

    create table conversation (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_type varchar(255) not null check (conversation_type in ('ONE_TO_ONE','GROUP')),
        creator_id bigint not null,
        deleted boolean not null,
        last_message_id bigint,
        last_message_sent timestamp(6) with time zone,
        public_id uuid,
        primary key (id)
    );

    create table conversation_participant (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_id bigint not null,
        last_read_message_id bigint,
        role varchar(20) not null check (role in ('ADMIN','MEMBER')),
        user_id bigint not null,
        primary key (id)
    );

    create table group_ (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_id bigint not null,
        group_name varchar(255) not null,
        group_profile_picture varchar(500),
        last_active timestamp(6) with time zone,
        public_id uuid not null,
        primary key (id)
    );

    create table message (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT,
        conversation_id bigint not null,
        deleted boolean not null,
        delivered_at timestamp(6) with time zone,
        media_name varchar(100),
        media_url varchar(500),
        public_id uuid,
        read_at timestamp(6) with time zone,
        reply_to_message_id bigint,
        sender_id bigint not null,
        sent_at timestamp(6) with time zone,
        status varchar(20) not null check (status in ('PREPARE','SENT','DELIVERED','SEEN')),
        type varchar(20) not null check (type in ('TEXT','FILE','IMAGE','VIDEO')),
        primary key (id)
    );

    create table notification (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT not null,
        message_thumbnail varchar(500),
        public_id uuid,
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
        firstname varchar(255),
        last_active timestamp(6) with time zone,
        lastname varchar(255),
        profile_picture varchar(255),
        public_id uuid,
        username varchar(255),
        primary key (id)
    );

    create table user_authority (
        user_id bigint not null,
        authority_name varchar(50) not null,
        primary key (user_id, authority_name)
    );

    create table user_setting (
        user_id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        friend_request_setting varchar(255) check (friend_request_setting in ('EVERYONE','FRIENDS_OF_FRIENDS','NOBODY')),
        last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')),
        profile_visibility boolean,
        primary key (user_id)
    );

    alter table if exists contact_request 
       drop constraint if exists UKdxcpfb5ytd3nbpn5uo1hp7t6e;

    alter table if exists contact_request 
       add constraint UKdxcpfb5ytd3nbpn5uo1hp7t6e unique (public_id);

    alter table if exists conversation 
       drop constraint if exists UKgafcthea67ee76r7f01clcw81;

    alter table if exists conversation 
       add constraint UKgafcthea67ee76r7f01clcw81 unique (public_id);

    alter table if exists group_ 
       drop constraint if exists UKgdlqdiqvcgqbe1fspoysdsjpw;

    alter table if exists group_ 
       add constraint UKgdlqdiqvcgqbe1fspoysdsjpw unique (conversation_id);

    alter table if exists group_ 
       drop constraint if exists UKoqiok65unlg3ujawpiwdoy9nd;

    alter table if exists group_ 
       add constraint UKoqiok65unlg3ujawpiwdoy9nd unique (public_id);

    alter table if exists message 
       drop constraint if exists UKiv3kt17dk5u1v4n8bpqkyhqvd;

    alter table if exists message 
       add constraint UKiv3kt17dk5u1v4n8bpqkyhqvd unique (public_id);

    alter table if exists notification 
       drop constraint if exists UK580xwhvqivevh4eucrgwqypnd;

    alter table if exists notification 
       add constraint UK580xwhvqivevh4eucrgwqypnd unique (public_id);

    alter table if exists user_ 
       drop constraint if exists UKha67cvlhy4nk1prswl5gj1y0y;

    alter table if exists user_ 
       add constraint UKha67cvlhy4nk1prswl5gj1y0y unique (email);

    alter table if exists user_ 
       drop constraint if exists UK7onw2v0fmn03fi86yrb29hegn;

    alter table if exists user_ 
       add constraint UK7onw2v0fmn03fi86yrb29hegn unique (public_id);

    alter table if exists user_ 
       drop constraint if exists UKwqsqlvajcne4rlyosglqglhk;

    alter table if exists user_ 
       add constraint UKwqsqlvajcne4rlyosglqglhk unique (username);

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

    alter table if exists conversation 
       add constraint FKk4ff054uhh47bajpq2ioterxo 
       foreign key (creator_id) 
       references user_;

    alter table if exists conversation 
       add constraint FKsm3966podppo987o2etdjci1r 
       foreign key (last_message_id) 
       references message;

    alter table if exists conversation_participant 
       add constraint FK93dv599s56uqs8xslhdp3arya 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists conversation_participant 
       add constraint FKhf6gkkuops56saeu2gqrj9pp9 
       foreign key (user_id) 
       references user_;

    alter table if exists group_ 
       add constraint FK1q8ojqw7oymq1axfw8ovbul5x 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists message 
       add constraint FK6yskk3hxw5sklwgi25y6d5u1l 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists message 
       add constraint FKm83oxlnewy7ev6dc1pxjwr4rn 
       foreign key (reply_to_message_id) 
       references message;

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
        public_id uuid,
        receiver_user_id bigint not null,
        request_user_id bigint not null,
        status varchar(255) check (status in ('PENDING','ACCEPTED','DECLINED','CANCELED')),
        primary key (id)
    );

    create table conversation (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_type varchar(255) not null check (conversation_type in ('ONE_TO_ONE','GROUP')),
        creator_id bigint not null,
        deleted boolean not null,
        last_message_id bigint,
        last_message_sent timestamp(6) with time zone,
        public_id uuid,
        primary key (id)
    );

    create table conversation_participant (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_id bigint not null,
        last_read_message_id bigint,
        role varchar(20) not null check (role in ('ADMIN','MEMBER')),
        user_id bigint not null,
        primary key (id)
    );

    create table group_ (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        conversation_id bigint not null,
        group_name varchar(255) not null,
        group_profile_picture varchar(500),
        last_active timestamp(6) with time zone,
        public_id uuid not null,
        primary key (id)
    );

    create table message (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT,
        conversation_id bigint not null,
        deleted boolean not null,
        delivered_at timestamp(6) with time zone,
        media_name varchar(100),
        media_url varchar(500),
        public_id uuid,
        read_at timestamp(6) with time zone,
        reply_to_message_id bigint,
        sender_id bigint not null,
        sent_at timestamp(6) with time zone,
        status varchar(20) not null check (status in ('PREPARE','SENT','DELIVERED','SEEN')),
        type varchar(20) not null check (type in ('TEXT','FILE','IMAGE','VIDEO')),
        primary key (id)
    );

    create table notification (
        id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        content TEXT not null,
        message_thumbnail varchar(500),
        public_id uuid,
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
        firstname varchar(255),
        last_active timestamp(6) with time zone,
        lastname varchar(255),
        profile_picture varchar(255),
        public_id uuid,
        username varchar(255),
        primary key (id)
    );

    create table user_authority (
        user_id bigint not null,
        authority_name varchar(50) not null,
        primary key (user_id, authority_name)
    );

    create table user_setting (
        user_id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        friend_request_setting varchar(255) check (friend_request_setting in ('EVERYONE','FRIENDS_OF_FRIENDS','NOBODY')),
        last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')),
        profile_visibility boolean,
        primary key (user_id)
    );

    alter table if exists contact_request 
       drop constraint if exists UKdxcpfb5ytd3nbpn5uo1hp7t6e;

    alter table if exists contact_request 
       add constraint UKdxcpfb5ytd3nbpn5uo1hp7t6e unique (public_id);

    alter table if exists conversation 
       drop constraint if exists UKgafcthea67ee76r7f01clcw81;

    alter table if exists conversation 
       add constraint UKgafcthea67ee76r7f01clcw81 unique (public_id);

    alter table if exists group_ 
       drop constraint if exists UKgdlqdiqvcgqbe1fspoysdsjpw;

    alter table if exists group_ 
       add constraint UKgdlqdiqvcgqbe1fspoysdsjpw unique (conversation_id);

    alter table if exists group_ 
       drop constraint if exists UKoqiok65unlg3ujawpiwdoy9nd;

    alter table if exists group_ 
       add constraint UKoqiok65unlg3ujawpiwdoy9nd unique (public_id);

    alter table if exists message 
       drop constraint if exists UKiv3kt17dk5u1v4n8bpqkyhqvd;

    alter table if exists message 
       add constraint UKiv3kt17dk5u1v4n8bpqkyhqvd unique (public_id);

    alter table if exists notification 
       drop constraint if exists UK580xwhvqivevh4eucrgwqypnd;

    alter table if exists notification 
       add constraint UK580xwhvqivevh4eucrgwqypnd unique (public_id);

    alter table if exists user_ 
       drop constraint if exists UKha67cvlhy4nk1prswl5gj1y0y;

    alter table if exists user_ 
       add constraint UKha67cvlhy4nk1prswl5gj1y0y unique (email);

    alter table if exists user_ 
       drop constraint if exists UK7onw2v0fmn03fi86yrb29hegn;

    alter table if exists user_ 
       add constraint UK7onw2v0fmn03fi86yrb29hegn unique (public_id);

    alter table if exists user_ 
       drop constraint if exists UKwqsqlvajcne4rlyosglqglhk;

    alter table if exists user_ 
       add constraint UKwqsqlvajcne4rlyosglqglhk unique (username);

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

    alter table if exists conversation 
       add constraint FKk4ff054uhh47bajpq2ioterxo 
       foreign key (creator_id) 
       references user_;

    alter table if exists conversation 
       add constraint FKsm3966podppo987o2etdjci1r 
       foreign key (last_message_id) 
       references message;

    alter table if exists conversation_participant 
       add constraint FK93dv599s56uqs8xslhdp3arya 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists conversation_participant 
       add constraint FKhf6gkkuops56saeu2gqrj9pp9 
       foreign key (user_id) 
       references user_;

    alter table if exists group_ 
       add constraint FK1q8ojqw7oymq1axfw8ovbul5x 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists message 
       add constraint FK6yskk3hxw5sklwgi25y6d5u1l 
       foreign key (conversation_id) 
       references conversation;

    alter table if exists message 
       add constraint FKm83oxlnewy7ev6dc1pxjwr4rn 
       foreign key (reply_to_message_id) 
       references message;

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
