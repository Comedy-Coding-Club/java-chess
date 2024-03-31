package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.ChessBoardDao;
import domain.game.ChessBoard;
import domain.piece.ChessBoardGenerator;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDaoTest {

    private final ChessBoardDao chessBoardDao = new ChessGameDao();

    @DisplayName("1. DB에 chessBoard를 저장한다.")
    @Test
    void saveChessBoard() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        Assertions.assertDoesNotThrow(() -> chessBoardDao.save(1, chessBoard));
    }

}
