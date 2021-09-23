create table driver (
    id bigint not null,
    name varchar(255),
    truck_id bigint,
    primary key (id)
);

create table truck (
    id bigint not null,
    brand varchar(255),
    model varchar(255),
    primary key (id)
);

alter table driver
    add constraint driver_truck_fk
    foreign key (truck_id) references truck;