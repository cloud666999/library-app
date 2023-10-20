create table book(
                            id               serial8    not null primary key ,
                            name            varchar(255)  not null,
                            author           varchar(45)  not null,
                            description      text default null,
                            copies           int default null,
                            copies_available int default null,
                            category         varchar(255) default null,
                            img              text default null

);

create index idx_books_name on book(name);