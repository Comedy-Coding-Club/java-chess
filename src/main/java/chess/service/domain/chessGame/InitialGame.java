package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public class InitialGame extends ChessGame {

    public InitialGame(int gameId) {
        super(gameId);
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public ChessGame startGame(Supplier<Boolean> checkRestart) {
        return new PlayingGame(1);
    }

    @Override
    public ChessGame endGame() {
        return new EndGame(getGameId(), Board.createEmptyBoard());
    }

    @Override
    public ChessGame move(Location source, Location target) {
        throw new IllegalStateException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Board getBoard() {
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
