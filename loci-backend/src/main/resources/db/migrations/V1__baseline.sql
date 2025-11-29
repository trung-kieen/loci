create sequence user_sequence start with 1 increment by 1;
create table authority (name varchar(50) not null, primary key (name));
create table user_ (friend_request_setting smallint check (friend_request_setting between 0 and 2), profile_visibility boolean, created_date timestamp(6) with time zone, id bigint not null, last_active timestamp(6) with time zone, last_modified_date timestamp(6) with time zone, last_seen timestamp(6) with time zone, public_id uuid, bio varchar(255), email varchar(255), firstname varchar(255), gender varchar(255) check (gender in ('MALE','FEMALE')), image_url varchar(255), last_seen_setting varchar(255) check (last_seen_setting in ('EVERYONE','CONTACT_ONLY','NOBODY')), lastname varchar(255), primary key (id));
create table user_authority (user_id bigint not null, authority_name varchar(50) not null, primary key (user_id, authority_name));
alter table if exists user_authority add constraint FK6ktglpl5mjosa283rvken2py5 foreign key (authority_name) references authority;
alter table if exists user_authority add constraint FKio2xcw9ogcqbasp25n5vttxrf foreign key (user_id) references user_;
