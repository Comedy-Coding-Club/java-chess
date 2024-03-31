package domain.game;

import domain.piece.ChessBoardGenerator;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class ChessGame {
    private final ChessBoard chessBoard;
    private Color color;
    private GameState gameState;

    public ChessGame() {
        this.chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        this.color = Color.WHITE;
        this.gameState = GameState.READY;
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
        chessBoard.checkRoute(source, target, color);
        chessBoard.move(source, target);

        if (chessBoard.isKingDeath()) {
            end();
            return;
        }

        color = color.reverseColor();
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

    public boolean isEnd() {
        return gameState.isEnd();
    }

    public boolean isNotEnd() {
        return gameState.isNotEnd();
    }

    public Color getColor() {
        return color;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Map<Position, Piece> getChessBoard() {
        return chessBoard.getPiecesPosition();
    }
}
