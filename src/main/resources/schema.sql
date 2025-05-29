create table if not exists `service`(
    id int not null,
    title varchar(100) not null,
    created_at varchar(100) not null,
    approved_at varchar(100),
    created_by varchar(255) not null,
    primary key (id)
);

create table if not exists `parameter`(
    id int not null,
    title varchar(100) not null,
    created_at varchar(100) not null,
    updated_at varchar(100) not null,
    primary key (id)
);

create table if not exists `class`(
     id int not null,
    title varchar(100) not null,
    created_at varchar(100) not null,
    updated_at varchar(100),
    primary key (id)
);

--create table if not exists service_parameters(
--    service_id int not null,
--    parameter_id int not null,
--    foreign key  (service_id) references `service`(id),
--    foreign key  (parameter_id) references `parameter`(id)
--);