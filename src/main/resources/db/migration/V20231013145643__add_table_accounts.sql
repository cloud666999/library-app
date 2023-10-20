create table account(
                        id serial8 not null primary key,
                        username varchar(255) not null,
                        password varchar(255) not null,
                        name varchar(255) not null,
                        role varchar(255) not null,
                        created_at timestamp not null default now(),
                        updated_at timestamp not null default now()
);

create unique index account_username_uindex on account(username);