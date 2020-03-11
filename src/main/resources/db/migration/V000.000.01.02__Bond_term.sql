create table bond_terms (
  id             bigint identity primary key,
  bond_id        bigint          not null,
  effective_time timestamp       not null,
  end_date       date            not null,
  interest_rate  decimal(11, 10) not null
);

alter table bond_terms
  add constraint fk_bond_term_bond foreign key (bond_id)
references bonds (id) on delete cascade;
