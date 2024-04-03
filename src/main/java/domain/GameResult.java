package domain;

import controller.constants.Winner;
import domain.game.ChessBoard;
import domain.piece.Color;

public record GameResult(
        Winner winner,
        double blackScore,
        double whiteScore
) {
    public static GameResult from(final ChessBoard chessBoard) {
        double blackScore = chessBoard.calculateScore(Color.BLACK);
        double whiteScore = chessBoard.calculateScore(Color.WHITE);
        Winner winner = judge(blackScore, whiteScore);
        return new GameResult(winner, blackScore, whiteScore);
    }

    private static Winner judge(final double blackScore, final double whiteScore) {
        if (blackScore == whiteScore) {
            return Winner.TIE;
        }
        if (blackScore > whiteScore) {
            return Winner.BLACK;
        }
        return Winner.WHITE;
    }
}
