drop table if exists TODO CASCADE;
drop table if exists PERSON CASCADE;
 
create table if not exists PERSON(ID bigint PRIMARY KEY AUTO_INCREMENT, NAME varchar(255) not null);
create table if not exists TODO(ID bigint PRIMARY KEY AUTO_INCREMENT, NAME varchar(255) not null, IS_COMPLETE boolean not null, PERSON_ID bigint);