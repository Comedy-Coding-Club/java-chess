CREATE TABLE board
(
    piece_type VARCHAR (20) NOT NULL,
    column_index INT NOT NULL,
    row_index INT NOT NULL,
    color VARCHAR (20) NOT NULL
);

CREATE TABLE game
(
    id INT PRIMARY KEY AUTO_INCREMENT ,
    current_turn VARCHAR (20) NOT NULL
);

INSERT INTO game (current_turn) VALUES ('WHITE');
