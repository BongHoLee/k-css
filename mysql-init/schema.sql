create table if not exists css_db.account
(
    account_id bigint auto_increment primary key,
    age        int          not null,
    residence  varchar(255) null
);

create table if not exists css_db.payment
(
    payment_id    bigint auto_increment primary key,
    amount        double       not null,
    item_category varchar(255) not null,
    method_type   varchar(255) not null,
    region        varchar(255) not null,
    account_id    bigint       not null
    );
create table if not exists css_db.pgroup
(
    group_id    bigint auto_increment primary key,
    conditions  json         not null,
    information varchar(255) null
    );
