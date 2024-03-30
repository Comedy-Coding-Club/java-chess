package chess.domain.board;

import static chess.domain.ChessGame.START_COLOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBChessBoardTest {

    private Connection connection;
    private ChessBoard chessBoard;

    @BeforeEach
    void beforeEach() throws SQLException {
        connection = DBConnectionUtils.getConnection();
        BoardDao boardDao = new BoardDao(connection);
        chessBoard = new DBChessBoard(boardDao);
        connection.setAutoCommit(false);
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.rollback();
    }

    @DisplayName("보드를 초기화한다.")
    @Test
    void initBoardTest() {
        //given
        Map<Position, Piece> defaultBoard = DefaultBoardInitializer.initializer();

        //when
        chessBoard.initNewBoard(START_COLOR);
        Map<Position, Piece> board = chessBoard.getBoard();

        //then
        assertThat(board).isEqualTo(defaultBoard);
    }

    @DisplayName("특정 위치에 기물을 추가할 수 있다.")
    @Test
    void putPieceTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        chessBoard.putPiece(position, piece);

        //then
        assertThat(chessBoard.findPieceByPosition(position)).isEqualTo(piece);
    }

    @DisplayName("기물을 이동시킨다.")
    @Test
    void movePieceTest() {
        //given
        Position from = new Position(Row.RANK2, Column.C);
        Position to = new Position(Row.RANK3, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        chessBoard.putPiece(from, piece);
        chessBoard.movePiece(from, to);

        //then
        assertThatThrownBy(() -> chessBoard.findPieceByPosition(from))
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(chessBoard.findPieceByPosition(to)).isEqualTo(piece);
    }

    @DisplayName("해당 위치에 기물이 있으면 true 를 리턴한다.")
    @Test
    void hasPieceTrueTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        chessBoard.putPiece(position, piece);

        //then
        assertThat(chessBoard.hasPiece(position)).isTrue();
    }

    @DisplayName("해당 위치에 기물이 없으면 false 를 리턴한다.")
    @Test
    void hasPieceFalseTest() {
        //given
        Position position = new Position(Row.RANK5, Column.C);

        //when
        chessBoard.initNewBoard(START_COLOR);

        //then
        assertThat(chessBoard.hasPiece(position)).isFalse();
    }

    @DisplayName("해당 위치에 기물이 없으면 true 를 리턴한다.")
    @Test
    void isEmptySpaceTrueTest() {
        //given
        Position position = new Position(Row.RANK5, Column.C);

        //when
        chessBoard.initNewBoard(START_COLOR);

        //then
        assertThat(chessBoard.isEmptySpace(position)).isTrue();
    }

    @DisplayName("해당 위치에 기물이 있으면 False 를 리턴한다.")
    @Test
    void isEmptySpaceFalseTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        chessBoard.putPiece(position, piece);

        //then
        assertThat(chessBoard.isEmptySpace(position)).isFalse();
    }

    @DisplayName("보드판에 왕이 2명이 있으면 true 를 리턴한다")
    @Test
    void hasTowKingTrueTest() {
        //when
        chessBoard.initNewBoard(START_COLOR);

        //then
        assertThat(chessBoard.hasTwoKing()).isTrue();
    }

    @DisplayName("보드판에 왕이 2명이 있으면 false 를 리턴한다")
    @Test
    void hasTowKingFalseTest() {
        //given
        Piece whiteKing = new Piece(PieceType.KING, Color.WHITE);
        Piece blackRook = new Piece(PieceType.ROOK, Color.BLACK);

        //when
        chessBoard.putPiece(new Position(Row.RANK1, Column.D), whiteKing);
        chessBoard.putPiece(new Position(Row.RANK8, Column.D), blackRook);

        //then
        assertThat(chessBoard.hasTwoKing()).isFalse();
    }
}
