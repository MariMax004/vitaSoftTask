create sequence role_seq;
create table role
(
    id   long primary key,
    name text
);

create sequence customer_seq;
create table customer
(
    id            long primary key,
    name          text,
    last_name     text,
    birthday_date date,
    email         text,
    password      text
);


create sequence token_seq;
create table token
(
    id          long primary key,
    customer_id long,
    token       text,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);


create sequence app_seq;
create table application
(
    id          long primary key,
    customer_id long,
    name        text,
    comment     text,
    status      text,
    date_create date,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);



create table customer_role
(
    customer_id long references customer (id),
    role_id     long references role (id),
    unique (customer_id, role_id)
);

