package chess.domain;

import chess.domain.board.Board;
import chess.domain.dbUtils.BoardDao;
import chess.domain.board.MysqlBoardInitializer;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlDefaultBoardInitializerTest {

    private Connection connection;

    @BeforeEach
    void beforeEach() {
        connection = BoardDao.getConnection();
        try {
            connection.prepareStatement("set autocommit false");
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

//    @AfterEach
//    void afterEach() {
//        try {
//            connection.prepareStatement("rollback");
//        } catch (SQLException e) {
//            throw new IllegalStateException(e);
//        }
//    }

    @DisplayName("DB에 접근하여 Board 를 초기화 할 수 있다.")
    @Test
    void initializeTest() {

        MysqlBoardInitializer initializer = new MysqlBoardInitializer(connection);

        Board board = initializer.initialize();

        Assertions.assertThat(board).isNotNull();
    }
}
