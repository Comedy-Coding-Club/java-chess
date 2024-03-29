package chess.domain;

import chess.domain.board.MemoryChessBoard;
import chess.domain.board.MysqlBoardInitializer;
import chess.domain.dbUtils.DBConnectionUtils;
import java.sql.Connection;
import java.sql.SQLException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MysqlDefaultMemoryChessBoardInitializerTest {

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

    @DisplayName("DB에 접근하여 Board 를 초기화 할 수 있다.")
    @Test
    void initializeTest() {

        MysqlBoardInitializer initializer = new MysqlBoardInitializer(connection);

        MemoryChessBoard memoryChessBoard = initializer.initialize();

        Assertions.assertThat(memoryChessBoard).isNotNull();
    }
}
