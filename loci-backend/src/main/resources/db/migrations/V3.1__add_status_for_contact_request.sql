    alter table if exists contact_request
       add column status varchar(255) check (status in ('PENDING','ACCEPTED','DECLINED','CANCELED'));
