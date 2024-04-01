create table chess_board
(
    id            int auto_increment
        primary key,
    chess_game_id int         null,
    board_file    varchar(20) null,
    board_rank    varchar(20) null,
    piece_type    varchar(20) not null,
    piece_color   varchar(20) not null
);

create table chess_game
(
    id          int auto_increment
        primary key,
    game_status varchar(20) not null,
    color       varchar(20) not null
);

