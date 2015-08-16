create table contacts (
	id identity,
	firstName varchar(30) not null,
	lastName varchar(50) not null,
	phoneNumber varchar(13),
	emailAddress varchar(30)
);

create table blogs (
	id identity,
	author varchar(30) not null,
	title varchar(50) not null,
	url varchar(50) not null
);