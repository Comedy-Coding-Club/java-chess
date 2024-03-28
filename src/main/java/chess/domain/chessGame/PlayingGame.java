package chess.domain.chessGame;

import chess.domain.board.Board;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import java.util.Map;
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
    public Map<Location, Piece> getBoard() {
        return board.getBoard();
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
