CREATE DATABASE DeepSeat DEFAULT CHARACTER SET utf8 collate utf8_bin;

use DeepSeat;

create table user
(
    userID   varchar(10) not null primary key,
    userPW   varchar(64) not null,
    salt     varchar(10) not null,
    nickname varchar(10) not null
);

create table document
(
    docID   integer      not null primary key auto_increment,
    userID  varchar(10)  not null,
    roomID  integer      not null,
    seatID  integer      not null,
    content varchar(100) not null,
    wrote   datetime     not null default now(),
    edited  boolean               default false,
    foreign key (userID) references user (userID)
);

create table comment
(
    commentID integer      not null primary key auto_increment,
    userID    varchar(10)  not null,
    docID     integer               default null,
    content   varchar(100) not null,
    wrote     datetime     not null default now(),
    edited    boolean               default false,
    foreign key (userID) references user (userID),
    foreign key (docID) references document (docID)
);

create table liked
(
    likedID   integer     not null primary key auto_increment,
    userID    varchar(10) not null,
    docID     integer default null,
    commentID integer default null,
    foreign key (userID) references user (userID),
    foreign key (docID) references document (docID),
    foreign key (commentID) references comment (commentID)
);

create table room
(
    roomID   integer     not null primary key auto_increment,
    roomName varchar(30) not null
);

create table seat
(
    seatID integer not null primary key auto_increment,
    roomID integer not null,
    x      integer not null,
    y      integer not null,
    width  integer not null,
    height integer not null,
    foreign key (roomID) references room (roomID)
);

create table observation
(
    observerID bigint      not null primary key auto_increment,
    roomID     integer     not null,
    seatID     integer     not null,
    date       varchar(50) not null,
    state      integer     not null,
    foreign key (roomID) references room (roomID),
    foreign key (seatID) references seat (seatID)
);