
# Back-end Server SQL

## 터미널 mariadb 접속 방법
```
$ mysql -u root -p
// 비밀번호 입력
```
```
mysql> USE DeepSeat;
Database changed
```

## Scheme
```sql
CREATE DATABASE DeepSeat DEFAULT CHARACTER SET utf8mb4 collate utf8mb4_unicode_ci;
```

## Table
### User
```sql
create table user
(
    userID   varchar(10) not null primary key,
    userPW   varchar(64) not null,
    salt     varchar(10) not null,
    nickname varchar(10) not null,
    email    varchar(50) not null,
    verified boolean     not null default false
);
```

### Document
```sql
create table document(
    docID integer not null primary key auto_increment,
    userID varchar(10) not null,
    roomID integer not null,
    seatID integer not null,
    content varchar(100) not null,
    wrote datetime not null default now(),
    edited boolean default false ,
    foreign key (userID) references user (userID)
);
```

### Comment
```sql
create table comment(
    commentID integer not null primary key auto_increment,
    userID varchar(10) not null,
    docID integer default null ,
    content varchar(100) not null,
    wrote datetime not null default now(),
    edited boolean default false,
    foreign key (userID) references user (userID),
    foreign key (docID) references document(docID)
);
```

### Liked
```sql
create table liked(
    likedID integer not null primary key auto_increment,
    userID varchar (10) not null,
    docID integer default null,
    commentID integer default null,
    foreign key (userID) references user (userID),
    foreign key (docID) references document(docID),
    foreign key (commentID) references comment (commentID)
);
```

### Seat
```sql
create table seat(
    seatID integer not null primary key auto_increment,
    roomID integer not null,
    x integer not null,
    y integer not null,
    width integer not null,
    height integer not null,
    foreign key (roomID) references room (roomID)
);
```

### Room
```sql
create table room(
    roomID integer not null primary key auto_increment,
    roomName varchar (30) not null
);
```
### Observation
```sql
create table observation
(
    observerID bigint  not null primary key auto_increment,
    roomID     integer not null,
    seatID     integer not null,
    `date`     date    not null default now(),
    `time`     time    not null default now(),
    state      integer not null,
    foreign key (roomID) references room (roomID),
    foreign key (seatID) references seat (seatID)
);
```