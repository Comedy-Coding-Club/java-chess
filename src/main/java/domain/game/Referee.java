package domain.game;

import controller.constants.Winner;
import controller.dto.GameResult;
import domain.piece.Color;

public class Referee {
    private final ChessBoard chessBoard;

    public Referee(final ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public GameResult judge() {
        double blackScore = chessBoard.calculateScore(Color.BLACK);
        double whiteScore = chessBoard.calculateScore(Color.WHITE);
        Winner winner = generateWinner(blackScore, whiteScore);
        return new GameResult(winner, blackScore, whiteScore);
    }

    private Winner generateWinner(final double blackScore, final double whiteScore) {
        if (blackScore == whiteScore) {
            return Winner.TIE;
        }
        if (blackScore > whiteScore) {
            return Winner.BLACK;
        }
        return Winner.WHITE;
    }
}
