CREATE TABLE BOARDS
(
    location  char(2) NOT NULL ,
    piece_type varchar(12) NOT NULL,
    color char(5) NOT NULL
);

CREATE TABLE GAMES(
    turn char(5) NOT NULL
);
