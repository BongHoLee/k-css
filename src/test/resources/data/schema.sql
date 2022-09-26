create table if not exists account
(
    account_id bigint auto_increment primary key,
    age        int          not null,
    residence  varchar(255) null
);

create table if not exists payment
(
    payment_id    bigint auto_increment primary key,
    amount        double       null,
    item_category varchar(255) null,
    method_type   varchar(255) null,
    region        varchar(255) null,
    account_id    bigint       not null
    );

