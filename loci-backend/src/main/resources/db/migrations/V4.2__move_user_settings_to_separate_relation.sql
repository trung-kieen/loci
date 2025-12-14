    create table user_settings (
        user_id bigint not null,
        created_date timestamp(6) with time zone,
        last_modified_date timestamp(6) with time zone,
        friend_request_setting varchar(255) check (friend_request_setting in ('EVERYONE','FRIENDS_OF_FRIENDS','NOBODY')),
        last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')),
        profile_visibility boolean,
        primary key (user_id)
    );

    alter table if exists user_settings
       add constraint FKp3nomjf43s475m64i9q73arb0
       foreign key (user_id)
       references user_;
    alter table user_ drop column friend_request_setting;
    alter table user_ drop column last_seen_setting;
    alter table user_ drop column profile_visibility;
