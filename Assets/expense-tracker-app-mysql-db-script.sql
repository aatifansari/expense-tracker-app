drop database expensetrackerdb;
create database expensetrackerdb;
use expensetrackerdb;

create table users( 
user_id int primary key not null auto_increment,
first_name varchar(20) not null,
last_name varchar(20) not null,
email varchar(30) not null,
password varchar(200) not null,
age int not null,
dob varchar(20) not null
)Engine=InnoDB auto_increment =1;

create table categories(
category_id int primary key not null auto_increment,
user_id int not null,
title varchar(20) not null,
description varchar(50) not null,
constraint foreign key (user_id) references users(user_id)
)engine =InnoDB auto_increment=1;

create table transactions(
transaction_id int primary key not null auto_increment,
user_id int not null,
category_id int not null,
amount decimal(10,2) not null,
note varchar(50) not null,
transaction_date bigint not null,
constraint foreign key(user_id) references users(user_id),
constraint foreign key(category_id) references categories(category_id) 
)Engine=InnoDB auto_increment=1000;




