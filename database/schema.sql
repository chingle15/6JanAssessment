CREATE TABLE customers
(
    id VARCHAR(32) not null,
    name varchar(32) not null,
    address varchar(128),
    email varchar(128),
    primary key(id)
);