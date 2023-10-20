create table checkout(
                     id               serial8    not null primary key ,
                     checkout_date     timestamptz not null  default now(),
                     returned_date      timestamptz not null  default  now()
);

