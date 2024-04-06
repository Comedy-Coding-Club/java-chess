USE chess;
-- auto-generated definition
create table if not exists boards
(
    board_id     int primary key auto_increment,
    current_turn varchar(10) not null
);


create table if not exists squares
(
    board_id    int   not null,
    square_file tinyint     not null,
    square_rank tinyint     not null,
    color       varchar(10) not null,
    piece_type  varchar(10) not null,
    primary key (board_id, square_file, square_rank)
);

