create table manager
(
    id         bigint       not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    password   varchar(255)  not null,
    phone      varchar(11) not null,
    position   varchar(255),
    salt       integer      not null,
    primary key (id)
) engine=InnoDB

create table product
(
    id                 bigint       not null auto_increment,
    created_at         datetime(6) not null,
    updated_at         datetime(6),
    barcode            varchar(255) not null,
    category           varchar(255) not null,
    code               varchar(255) not null,
    manufacturer_code  varchar(255) not null,
    product_identifier varchar(255) not null,
    content            varchar(255) not null,
    cost               bigint       not null,
    expiration_date    datetime(6) not null,
    name               varchar(255) not null,
    name_prefix        varchar(255),
    price              bigint       not null,
    size               varchar(255) not null,
    manager_id         bigint,
    primary key (id)
) engine=InnoDB

create table token
(
    id         bigint       not null auto_increment,
    created_at datetime(6) not null,
    updated_at datetime(6),
    access     varchar(255),
    expire_at  datetime(6),
    refresh    varchar(255) not null,
    manager_id bigint,
    primary key (id)
) engine=InnoDB

alter table product
    add constraint UK_44c6umvphppa3226vhmagmviu unique (barcode)
alter table product
    add constraint FKjr0yud7fty8ma1jvn0hif3no7 foreign key (manager_id) references manager (id)
alter table token
    add constraint FKbwlbmjefpnkh3tbkckjqanhxu foreign key (manager_id) references manager (id)