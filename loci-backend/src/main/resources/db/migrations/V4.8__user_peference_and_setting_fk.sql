
alter table if exists user_settings
  add constraint FKp3nomjf43s475m64i9q73arb0
    foreign key (user_id)
    references user_;
