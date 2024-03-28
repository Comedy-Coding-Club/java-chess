package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.BoardDto;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardDaoTest {

    private Connection connection;

    @BeforeEach
    void beforeEach() {
        try {
            connection = BoardDao.getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @AfterEach
    void afterEach() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @DisplayName("새로운 정보 추가 시 DB 에서 추가가 된다.")
    @Test
    void addTest() {
        //given
        Position position = new Position(Row.RANK3, Column.C);
        Piece piece = new Piece(PieceType.ROOK, Color.WHITE);
        BoardDto boardDto = new BoardDto(position, piece);

        //when
        BoardDao.create(connection, boardDto);
        BoardDto resultDto = BoardDao.findByPosition(connection, position);

        //then
        assertAll(
                () -> assertThat(resultDto.piece()).isEqualTo(piece),
                () -> assertThat(resultDto.position()).isEqualTo(position)
        );
    }
}
