package repository.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.game.ChessBoard;
import domain.game.ChessBoardGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ChessBoardDaoTest {
    private final FakeChessBoardDao chessBoardDao = new FakeChessBoardDao();

    @BeforeEach
    void save() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        chessBoardDao.save(chessBoard);
    }

    @AfterEach
    void delete() {
        chessBoardDao.delete();
    }

    @DisplayName("1. DB에 chessBoard를 저장한다.")
    @Test
    void saveChessBoard() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        assertDoesNotThrow(() -> chessBoardDao.save(chessBoard));
    }

    @DisplayName("2. DB에서 chessBoard를 찾는다.")
    @Test
    void findByChessGameId() {
        assertThat(chessBoardDao.findByChessGameId()).isPresent();
    }

    @DisplayName("3. DB에서 chessBoard를 삭제 한다.")
    @Test
    void deleteChessBoard() {
        chessBoardDao.delete();
        assertThat(chessBoardDao.findByChessGameId()).isNotPresent();
    }
}
