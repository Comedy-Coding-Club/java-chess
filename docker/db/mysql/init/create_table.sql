CREATE TABLE GAMES(
    game_id int NOT NULL,
    turn char(5) NOT NULL,
    PRIMARY KEY (game_id)
);

CREATE TABLE BOARDS
(
    game_id int NOT NULL,
    location  char(2) NOT NULL ,
    piece_type varchar(12) NOT NULL,
    color char(5) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES GAMES(game_id)
);
