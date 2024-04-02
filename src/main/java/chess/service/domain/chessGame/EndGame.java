package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.chessGame.exception.NotEndGameException;
import chess.service.domain.chessGame.exception.NotPlayingGameException;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public class EndGame extends ChessGame {
    private final Board board;

    public EndGame(int gameId, Board board) {
        super(gameId);
        this.board = board;
    }

    @Override
    public boolean isEnd() {
        return true;
    }

    @Override
    public ChessGame startGame(Supplier<Boolean> checkRestart) {
        return new PlayingGame(getGameId());
    }

    @Override
    public ChessGame endGame() {
        return this;
    }

    @Override
    public ChessGame move(Location source, Location target) {
        throw new NotPlayingGameException("이미 게임이 종료되었습니다.");
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
        if (!board.isKingDead()) {
            throw new NotEndGameException("아직 승부가 나지 않았습니다.");
        }
        return board.getWinner();
    }

    @Override
    public Color getTurn() {
        throw new NotPlayingGameException("이미 게임이 종료되었습니다.");
    }
}
