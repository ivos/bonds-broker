create table ip_address_requests (
  id             bigint identity primary key,
  effective_date date         not null,
  ip             varchar(100) not null
);
