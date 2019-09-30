SET NAMES utf8;

DROP DATABASE IF EXISTS st4db;

CREATE  DATABASE st4db CHARACTER SET utf8;

USE st4db;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS prices;
DROP TABLE IF EXISTS orders;

CREATE TABLE roles(

-- id has the INTEGER type (other name is INT), it is the primary key
	id INTEGER NOT NULL PRIMARY KEY,

-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE) 	
	name VARCHAR(10) NOT NULL UNIQUE
);

-- inserting data do roles table
----------------------------------------------------------------
-- IMPORTANT
-- we use ENUM as the Role entity, so the numeration must started 
-- from 0 with the step equaled to 1
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');

CREATE TABLE users(
	-- id has the INT, it is the primary key
	id INT NOT NULL PRIMARY KEY auto_increment,
	login VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    status boolean default TRUE,
    money INT NOT NULL default 0,
    locale_name VARCHAR(2) default "en",
    role_id INT NOT NULL REFERENCES roles(id) ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO users VALUES (default,'admin','admin','Yurij','Antonov',default,default,default,0);
INSERT INTO users VALUES (default,'user','user','yuriy','antonov',default,default,default,1);
INSERT INTO users VALUES (default,'yuriy','123','yuriy','antonov',FALSE,default,default,0);
INSERT INTO users VALUES (default,'ivan','111','ivan','ivanov',FALSE,default,default,1);
INSERT INTO users VALUES (default,'петр','222','петр','петров',default,default,default,1);
INSERT INTO users VALUES (default,'Jim','333','Jim','Carrey',default,default,default,1);

CREATE TABLE services (
id INT NOT NULL PRIMARY KEY auto_increment,
s_name VARCHAR (50) NOT NULL UNIQUE
);

INSERT INTO services values (default, 'Телефон');
INSERT INTO services values (default, 'Мобильный');
INSERT INTO services values (default, 'Интернет');
INSERT INTO services values (default, 'Кабельное');
INSERT INTO services values (default, 'IP-TV');

CREATE TABLE services_types (
id INT NOT NULL auto_increment,
st_id INT NOT NULL REFERENCES services(id) ON DELETE CASCADE ON UPDATE CASCADE,
st_info VARCHAR (50) NOT NULL,
st_price INT NOT NULL default 1,
PRIMARY KEY (id, st_id)
);

INSERT INTO services_types VALUES (default, 1, 'Тариф_1', 5); 
INSERT INTO services_types VALUES (default, 1, 'Тариф_2', 33);
INSERT INTO services_types VALUES (default, 2, 'Городской', 11);
INSERT INTO services_types VALUES (default, 2, 'По стране', 15);
INSERT INTO services_types VALUES (default, 3, '10 Мбит', 10);
INSERT INTO services_types VALUES (default, 3, '100 Мбит', 30);
INSERT INTO services_types VALUES (default, 4, 'Мало каналов',17);
INSERT INTO services_types VALUES (default, 4, 'Много каналов', 25);
INSERT INTO services_types VALUES (default, 5, 'Мало IP каналов', 14);
INSERT INTO services_types VALUES (default, 5, 'Много IP каналов', 21);

-- MySQL retrieves and displays DATE values in 'YYYY-MM-DD' format.

CREATE TABLE orders (
id INT NOT NULL primary key auto_increment,
o_user_id INT NOT NULL references user(id) ON DELETE CASCADE ON UPDATE CASCADE,
o_st_id INT NOT NULL references services_types (id) ON DELETE CASCADE ON UPDATE CASCADE,
o_date Date,
o_status boolean default FALSE
);

INSERT INTO orders VALUES (default, 1, 2, '1992-12-13', default);
INSERT INTO orders VALUES (default, 2, 3, '1996-10-23', TRUE);
INSERT INTO orders VALUES (default, 3, 5, '2001-05-21', default);
INSERT INTO orders VALUES (default, 4, 7, '2011-02-14', TRUE);
INSERT INTO orders VALUES (default, 5, 8, '2015-03-05', TRUE);
INSERT INTO orders VALUES (default, 1, 4, '2015-03-05', FALSE);

SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM services;
SELECT * FROM services_types;
SELECT * FROM orders;