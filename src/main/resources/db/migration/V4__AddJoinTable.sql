alter table driver drop truck_id;
create table driver_trucks (driver_id bigint not null, trucks_id bigint not null);
alter table driver_trucks add constraint driver_trucks_truck_constraint_fk foreign key (trucks_id) references truck;
alter table driver_trucks add constraint driver_trucks_driver_constraint_fk foreign key (driver_id) references driver;