package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public class PlayingGame implements ChessGame {
    private final Board board;
    private final Color turn;

    protected PlayingGame() {
        this(Board.createInitialBoard(), Color.WHITE);
    }

    public PlayingGame(Board board, Color turn) {
        this.board = board;
        this.turn = turn;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    @Override
    public ChessGame startGame(Supplier<Boolean> checkRestart) {
        if (checkRestart.get()) {
            return new PlayingGame();
        }
        return this;
    }

    @Override
    public ChessGame endGame() {
        return new EndGame(board);
    }

    @Override
    public ChessGame move(Location source, Location target) {
        board.move(source, target, turn);
        if (board.isKingDead()) {
            return new EndGame(board);
        }
        return new PlayingGame(board, turn.getOpponent());
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Score getScore(Color color) {
        return board.calculateScore(color);
    }

    @Override
    public Color getWinner() {
        throw new IllegalStateException("아직 승부가 나지 않았습니다.");
    }

    @Override
    public Color getTurn() {
        return this.turn;
    }
}
