package chess.domain;

import chess.domain.dbUtils.DBConnectionUtils;
import chess.domain.dbUtils.MysqlBoardInitializer;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlBoardInitializerTest {

    private Connection connection;

    @BeforeEach
    void beforeEach() {
        connection = DBConnectionUtils.getConnection();
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
