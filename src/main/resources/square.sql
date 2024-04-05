use chess;

DROP TABLE if exists square;

CREATE TABLE square (
	position VARCHAR (2) NOT NULL,
	pieceType VARCHAR (9) NOT NULL,
    color VARCHAR (5) NOT NULL,
    chessGame_id INT NOT NULL
);