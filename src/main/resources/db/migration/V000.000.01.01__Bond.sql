create table bonds (
  id         bigint identity primary key,
  name       varchar(100)   not null,
  start_date date           not null,
  amount     decimal(13, 3) not null,
  reference  varchar(36)    not null
);
