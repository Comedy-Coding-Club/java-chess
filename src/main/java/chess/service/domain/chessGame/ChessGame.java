package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public abstract class ChessGame {

    private final int gameId;

    protected ChessGame(int gameId) {
        this.gameId = gameId;
    }

    public abstract boolean isEnd();

    public abstract ChessGame startGame(Supplier<Boolean> checkRestart);

    public abstract ChessGame endGame();

    public abstract ChessGame move(Location source, Location target);

    public abstract Board getBoard();

    public abstract Score getScore(Color color);

    public abstract Color getWinner();

    public abstract Color getTurn();

    public int getGameId() {
        return gameId;
    }
}
