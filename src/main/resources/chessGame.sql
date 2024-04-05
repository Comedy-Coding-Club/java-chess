use chess;

DROP TABLE if exists chessGame;

CREATE TABLE chessGame (
	chessGame_id INT NOT NULL,
    currentColor VARCHAR (5) NOT NULL,
    PRIMARY KEY (chessGame_id)
);