package chess.repository;

import static chess.domain.location.LocationFixture.B4;
import static chess.domain.location.LocationFixture.F3;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.PlayingGame;
import chess.domain.piece.Color;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Queen;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private static final ChessGame PLAYING_GAME = new PlayingGame(
            new Board(Map.of(
                    //A1, new WhitePawn(),
                    B4, new King(Color.BLACK),
                    F3, new Queen(Color.WHITE)
            )), Color.BLACK
    );
    private static final GameDao PIECE_DAO = new GameDao();

    @DisplayName("저장 및 조회 테스트")
    @Test
    void saveFindTest() {
        PIECE_DAO.saveGame(PLAYING_GAME);

        Optional<ChessGame> optionalLoadedGame = PIECE_DAO.loadGame();
        assertThat(optionalLoadedGame.isPresent()).isTrue();

        ChessGame chessGame = optionalLoadedGame.get();
        assertThat(chessGame.getBoard()).isEqualTo(PLAYING_GAME.getBoard());
        assertThat(chessGame.getTurn()).isEqualTo(PLAYING_GAME.getTurn());
    }
}
