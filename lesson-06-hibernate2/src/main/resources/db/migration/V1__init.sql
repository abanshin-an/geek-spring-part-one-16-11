create table product
(
    id          bigserial primary key,
    description varchar(255)   not null,
    name        varchar(255)   not null unique,
    price       numeric(19, 2) not null
);

create table customer
(
    id   bigserial primary key,
    name varchar(255) not null unique
);

create table purchase
(
    id          bigserial primary key,
    customer_id bigint references customer,
    creation_date timestamp without time zone default now()
);

create table purchase_item
(
    id       bigserial primary key,
    purchase_id bigint references purchase,
    product_id bigint references product,
    price    numeric(19, 2) not null,
    quantity bigint
);

INSERT INTO product (id, description, name, price)
VALUES (1, 'iPhone 13', 'iPhone', 900.00),
       (2, 'Google Pixel 6', 'Pixel', 700.00),
       (3, 'Huiawei P50Pro', 'Huawei', 800.00),
       (4, 'iaomi Mi MIX FOLD 256', 'Xiaomi', 850.00);

INSERT INTO customer (id, name)
VALUES (1, 'Arthur Clarke'),
       (2, 'Liu Cixin');