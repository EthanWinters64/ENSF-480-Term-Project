DROP DATABASE IF EXISTS MOVIES;
CREATE DATABASE MOVIES;
USE MOVIES;


DROP TABLE IF EXISTS MOVIE_LIST;
CREATE TABLE MOVIE_LIST(
    MovieID     int not null AUTO_INCREMENT,
    Title       varchar(25),
    Theatre     varchar(25),
    Showtime    int, -- get the time values from these by: hour = int % 100, minute = int - hour*100;
    Showdate	varchar(8), -- the date is stored in a YYYYMMDD string
    Seats       varchar(10), -- here, this will be a long chain of 0's. A 0 indicates a seat is open, a 1 means it's purchased, and a 2 means it's purchased by a registered user prior to annoucnments
    primary key (MovieID)
);

INSERT INTO MOVIE_LIST (Title, Theatre, Showtime, Showdate, Seats)
VALUES
('Batman', 'Chinook', 930, 20221213, '1111111111'),
('Batman', 'Chinook', 1230, 20221213, '1111111111'),
('Batman', 'Chinook', 1830, 20221213, '1111111111'),
('Batman and Robin', 'Chinook', 1530, 20221213, '1111111111'),
('Batman and Robin', 'Chinook', 1730, 20221213, '1111111111'),
('Batman and Robin', 'Chinook', 1930, 20221213, '1111111111'),
('Jaws', 'Chinook', 905, 20221213, '1111111111'), 
('Jaws', 'Chinook', 1200, 20221213, '1111111111'), 
('Jaws', 'Chinook', 1400, 20221213, '1111111111'), 
('Jurrasic Park', 'Chinook', 1045, 20221213, '1111111111'),
('Jurrasic Park', 'Chinook', 1245, 20221213, '1111111111'),
('Jurrasic Park', 'Chinook', 1445, 20221213, '1111111111'); -- showtime uses a 24-hour clock

DROP TABLE IF EXISTS USER_LIST;
CREATE TABLE USER_LIST(
	UserID		int,
    Username	varchar(50),
    Pass		varchar(50),
    RegDate		varchar(12),
    CardNum		varchar(19),
    CVV			varchar(3),
    Email		varchar(50),
    Paid		int,
    Tickets		varchar(20), -- this stores a line of MovieIDs of the Movies the user has tickets too, each ID is stored as 2 digits, and a user can have up to 10 tickets
    Seats		varchar(20), -- this stores the seat numbers of the corosponding movies in the same format as above
    primary key (UserID)
);

INSERT INTO USER_LIST (UserID, Username, Pass, RegDate, CardNum, CVV, Email, Paid, Tickets, Seats)
VALUES
(1, 'MrTest', 'MrTestsPassword', 2012-11-02, 3000-2000-1000-0000, 300, 'MrTest@gmail.com', 1, '!!!!!!!!!!!!!!!!!!!!', '!!!!!!!!!!!!!!!!!!!!'), -- this user has paid their annual fee
(2, 'TheUndercut', 'DeepBelow', 2022-10-04, 0000-1000-2000-3000, 700, "Don'tCallMeBaldy@gmail.com", 0, '!!!!!!!!!!!!!!!!!!!!', '!!!!!!!!!!!!!!!!!!!!'); -- this user has not paid their annual fee
 

