CREATE DATABASE DeepSeat DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_unicode_ci;

use DeepSeat;

create table DeepSeat.apikey
(
    apiKey  varchar(64) primary key not null,
    created timestamp               not null default now()
);

create table DeepSeat.user
(
    userID   varchar(50) not null primary key,
    userPW   varchar(64) not null,
    salt     varchar(10) not null,
    nickname varchar(10) not null,
    email    varchar(50) not null,
    verified boolean     not null default false
);

create table DeepSeat.document
(
    docID   integer      not null primary key auto_increment,
    userID  varchar(50)  not null,
    roomID  integer      not null,
    seatID  integer      not null,
    content varchar(100) not null,
    wrote   datetime     not null default now(),
    edited  boolean               default false,
    foreign key (userID) references user (userID)
);

create table DeepSeat.comment
(
    commentID integer      not null primary key auto_increment,
    userID    varchar(50)  not null,
    docID     integer               default null,
    content   varchar(100) not null,
    wrote     datetime     not null default now(),
    edited    boolean               default false,
    foreign key (userID) references user (userID),
    foreign key (docID) references document (docID)
);

create table DeepSeat.liked
(
    likedID   integer     not null primary key auto_increment,
    userID    varchar(50) not null,
    docID     integer default null,
    commentID integer default null,
    foreign key (userID) references user (userID),
    foreign key (docID) references document (docID),
    foreign key (commentID) references comment (commentID)
);

create table DeepSeat.room
(
    roomID    integer     not null primary key auto_increment,
    roomName  varchar(30) not null,
    apiKey    varchar(64) not null,
    latitude  double      not null,
    longitude double      not null,
    foreign key (apiKey) references DeepSeat.apikey (apiKey)
);

create table DeepSeat.seat
(
    seatID integer not null,
    roomID integer not null,
    x      integer not null,
    y      integer not null,
    width  integer not null,
    height integer not null,
    foreign key (roomID) references room (roomID),
    primary key (roomID, seatID)
);

create table DeepSeat.observation
(
    observerID bigint  not null primary key auto_increment,
    roomID     integer not null,
    seatID     integer not null,
    `date`     date    not null default now(),
    `time`     time    not null default now(),
    state      integer not null,
    foreign key (roomID, seatID) references seat (roomID, seatID)
);