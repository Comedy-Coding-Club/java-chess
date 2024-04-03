package chess.domain.chessGame;

import chess.domain.chessGame.exception.NotEndGameException;
import chess.domain.chessGame.exception.NotStartGameException;
import chess.domain.location.Location;
import chess.domain.board.Board;
import chess.domain.chessGame.exception.NotPlayingGameException;
import chess.domain.piece.Color;
import chess.domain.piece.Score;
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
        return new PlayingGame(getGameId());
    }

    @Override
    public ChessGame endGame() {
        return new EndGame(getGameId(), Board.createEmptyBoard());
    }

    @Override
    public ChessGame move(Location source, Location target) {
        throw new NotPlayingGameException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Board getBoard() {
        throw new NotStartGameException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Score getScore(Color color) {
        throw new NotStartGameException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Color getWinner() {
        throw new NotEndGameException("게임을 먼저 시작해야 합니다.");
    }

    @Override
    public Color getTurn() {
        throw new NotPlayingGameException("게임을 먼저 시작해야 합니다.");
    }
}
