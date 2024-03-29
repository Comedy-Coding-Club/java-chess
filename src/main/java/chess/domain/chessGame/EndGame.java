package chess.domain.chessGame;

import chess.domain.board.Board;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.Score;
import java.util.Map;
import java.util.function.Supplier;

public class EndGame implements ChessGame {
    private final Board board;

    public EndGame(Board board) {
        this.board = board;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public ChessGame startGame(Supplier<Boolean> checkRestart) {
        throw new IllegalStateException("이미 게임이 종료되었습니다.");
    }

    @Override
    public ChessGame endGame() {
        throw new IllegalStateException("이미 게임이 종료되었습니다.");
    }

    @Override
    public ChessGame move(Location source, Location target) {
        throw new IllegalStateException("이미 게임이 종료되었습니다.");
    }

    @Override
    public Map<Location, Piece> getBoard() {
        return board.getBoard();
    }

    @Override
    public Score getScore(Color color) {
        throw new IllegalStateException("이미 게임이 종료되었습니다.");
    }

    @Override
    public Color getWinner() {
        if (!board.isKingDead()) {
            throw new IllegalStateException("아직 승부가 나지 않았습니다.");
        }
        return board.getWinner();
    }

    @Override
    public Color getTurn() {
        throw new IllegalStateException("이미 게임이 종료되었습니다.");
    }
}
