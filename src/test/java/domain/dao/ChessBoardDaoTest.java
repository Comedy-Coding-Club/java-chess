package domain.dao;

import static org.assertj.core.api.Assertions.*;

import domain.game.ChessBoard;
import domain.piece.ChessBoardGenerator;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.DisplayName.class)
class ChessBoardDaoTest {
    private final ChessBoardDao chessBoardDao = new ChessBoardDao();

    @DisplayName("1. DB에 chessBoard를 저장한다.")
    @Test
    void saveChessBoard() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        Assertions.assertDoesNotThrow(() -> chessBoardDao.save(chessBoard));
    }

    @DisplayName("2. DB에서 chessBoard를 찾는다.")
    @Test
    void findByChessGameId() {
        ChessBoard foundChessBoard = chessBoardDao.findByChessGameId();
        Map<Position, Piece> foundPiecePosition = foundChessBoard.getPiecesPosition();

        assertThat(foundPiecePosition).containsAllEntriesOf(
                ChessBoardGenerator.generateInitialChessBoard().getPiecesPosition());
    }

    @DisplayName("3. DB에서 chessBoard를 삭제 한다.")
    @Test
    void delete() {
        assertThat(chessBoardDao.delete()).isEqualTo(32);
    }
}
