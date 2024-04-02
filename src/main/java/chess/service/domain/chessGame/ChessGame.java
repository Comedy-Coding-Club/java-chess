package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.chessGame.exception.NotEndGameException;
import chess.service.domain.chessGame.exception.NotPlayingGameException;
import chess.service.domain.chessGame.exception.NotStartGameException;
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

    public abstract ChessGame move(Location source, Location target) throws NotPlayingGameException;

    public abstract Board getBoard() throws NotStartGameException;

    public abstract Score getScore(Color color) throws NotStartGameException;

    public abstract Color getWinner() throws NotEndGameException;

    public abstract Color getTurn() throws NotPlayingGameException;

    public int getGameId() {
        return gameId;
    }
}
