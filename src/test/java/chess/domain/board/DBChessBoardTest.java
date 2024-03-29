package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.ChessGame;
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
        Position position = new Position(Row.RANK1, Column.C);

        //then
        assertThat(chessBoard.hasPiece(position)).isFalse();
    }

    @DisplayName("해당 위치에 기물이 없으면 true 를 리턴한다.")
    @Test
    void isEmptySpaceTrueTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);

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
        //given
        Piece whiteKing = new Piece(PieceType.KING, Color.WHITE);
        Piece blackKing = new Piece(PieceType.KING, Color.BLACK);

        //when
        chessBoard.putPiece(new Position(Row.RANK1, Column.D), whiteKing);
        chessBoard.putPiece(new Position(Row.RANK8, Column.D), blackKing);

        //then
        assertThat(chessBoard.hasKing(ChessGame.KING_COUNT)).isTrue();
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
        assertThat(chessBoard.hasKing(ChessGame.KING_COUNT)).isFalse();
    }
}
