package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import chess.db.BoardDao;
import chess.db.DBBoardRepository;
import chess.db.DBConnectionUtils;
import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBBoardRepositoryTest {

    private static Connection connection;
    private DBBoardRepository dbBoardRepository;

    @BeforeEach
    void beforeEach() throws SQLException {
        connection = DBConnectionUtils.getConnection();
        BoardDao boardDao = new BoardDao(connection);
        dbBoardRepository = new DBBoardRepository(boardDao);
        connection.setAutoCommit(false);
        ChessBoardService chessBoardService = new ChessBoardService(dbBoardRepository);
        chessBoardService.initNewBoard(DefaultBoardInitializer.initializer());
    }

    @AfterEach
    void afterEach() throws SQLException {
        connection.rollback();
    }

    @AfterAll
    static void afterAll() throws SQLException {
        connection.close();
    }

    @DisplayName("특정 위치에 기물을 추가할 수 있다.")
    @Test
    void putPieceTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        dbBoardRepository.placePiece(position, piece);

        //then
        assertThat(dbBoardRepository.findPieceByPosition(position)).isEqualTo(piece);
    }


    @DisplayName("해당 위치에 기물이 있으면 true 를 리턴한다.")
    @Test
    void hasPieceTrueTest() {
        //given
        Position position = new Position(Row.RANK1, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);

        //when
        dbBoardRepository.placePiece(position, piece);

        //then
        assertThat(dbBoardRepository.hasPiece(position)).isTrue();
    }

    @DisplayName("해당 위치에 기물이 없으면 false 를 리턴한다.")
    @Test
    void hasPieceFalseTest() {
        //given
        Position position = new Position(Row.RANK5, Column.C);

        //then
        assertThat(dbBoardRepository.hasPiece(position)).isFalse();
    }
}
