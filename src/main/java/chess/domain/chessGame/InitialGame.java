package chess.domain.chessGame;

import chess.domain.board.Board;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import java.util.Map;
import java.util.function.Supplier;

public class InitialGame implements ChessGame {

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public ChessGame startGame(Supplier<Boolean> checkRestart) {
        return new PlayingGame();
    }

    @Override
    public ChessGame endGame() {
        return new EndGame(Board.createEmptyBoard());
    }

    @Override
    public ChessGame move(Location source, Location target) {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Map<Location, Piece> getBoard() {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Score getScore(Color color) {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Color getWinner() {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Color getTurn() {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }
}
