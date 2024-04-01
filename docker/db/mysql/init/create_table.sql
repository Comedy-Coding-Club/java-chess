CREATE TABLE GAMES(
    id BIGINT NOT NULL,
    turn CHAR(5) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE BOARDS
(
    game_id BIGINT NOT NULL,
    location  CHAR(2) NOT NULL ,
    piece_type VARCHAR(12) NOT NULL,
    color CHAR(5) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES GAMES(id)
);
