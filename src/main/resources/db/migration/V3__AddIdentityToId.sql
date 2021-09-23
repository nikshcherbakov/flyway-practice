drop table truck cascade;
drop table driver;

create table driver (
                        id bigint not null identity,
                        name varchar(255),
                        truck_id bigint,
                        primary key (id)
);

create table truck (
                       id bigint not null identity,
                       brand varchar(255),
                       model varchar(255),
                       primary key (id)
);

alter table driver
    add constraint driver_truck_fk
        foreign key (truck_id) references truck;

insert into truck (brand, model) values ('Isuzu', 'Bad car');
insert into driver (name, truck_id) values ('Nikita', 1);