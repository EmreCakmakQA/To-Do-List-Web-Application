drop table if exists `todo` CASCADE;
drop table if exists `person` CASCADE;
 
create table if not exists person (`id` bigint PRIMARY KEY AUTO_INCREMENT, `name` varchar(255) not null);
create table if not exists todo(`id` bigint PRIMARY KEY AUTO_INCREMENT, `name` varchar(255) not null, `isComplete` boolean not null, person_id bigint);


