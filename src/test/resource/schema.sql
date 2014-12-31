drop table if exists spittle;
drop table if exists spitter;

create table spitter (
  id integer identity primary key,
  username varchar(25) not null,
  password varchar(25) not null,
  fullname varchar(100) not null,
  email varchar(50),
  update_by_email boolean
);

create table spittle (
  id integer identity primary key,
  spitter_id integer not null,
  spittleText varchar(2000) not null,
  postedTime DATETIME not null,
  foreign key (spitter_id) references spitter(id)
);
