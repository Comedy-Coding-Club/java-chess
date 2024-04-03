package domain.game;

import domain.piece.ChessBoardGenerator;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import domain.score.ScoreBoard;
import java.util.Map;

public class ChessGame {
    private ChessBoard chessBoard;
    private GameState gameState;

    public ChessGame() {
        this(ChessBoardGenerator.generateInitialChessBoard(), GameState.READY);
    }

    public ChessGame(ChessBoard chessBoard, GameState gameState) {
        this.chessBoard = chessBoard;
        this.gameState = gameState;
    }

    public void start() {
        if (gameState.isRunning()) {
            throw new IllegalStateException("이미 게임이 시작되었습니다.");
        }
        this.gameState = GameState.RUNNING;
    }

    public void move(Position source, Position target) {
        if (gameState.isNotRunning()) {
            throw new IllegalStateException("게임 진행중이 아닙니다.");
        }
        chessBoard.checkRoute(source, target);
        chessBoard.move(source, target);

        if (chessBoard.isKingDeath()) {
            end();
        }
    }

    public ScoreBoard status() {
        if (gameState.isNotRunning()) {
            throw new IllegalStateException("게임 진행 중이 아니므로, 점수를 계산할 수 없습니다.");
        }
        return chessBoard.calculateScore();
    }

    public void end() {
        if (gameState.isNotRunning()) {
            throw new IllegalStateException("게임 진행중이 아닙니다.");
        }
        gameState = GameState.END;
    }

    public void update(ChessBoard chessBoard, GameState gameState) {
        this.chessBoard = chessBoard;
        this.gameState = gameState;
    }

    public boolean isEnd() {
        return gameState.isEnd();
    }

    public boolean isNotEnd() {
        return gameState.isNotEnd();
    }

    public Color getColor() {
        return chessBoard.getColor();
    }

    public GameState getGameState() {
        return gameState;
    }

    public ChessBoard getBoard() {
        return chessBoard;
    }

    public Map<Position, Piece> getChessBoard() {
        return chessBoard.getPiecesPosition();
    }
}
