package chess.dto;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.util.ScoreCalculator;
import java.util.Map;

public record ScoresDto(double blackScore, double whiteScore) {

    public static ScoresDto fromChessBoard(final ChessBoard chessBoard) {
        final Map<Position, Piece> blackPieces = chessBoard.getPiecesWithPositionBy(Color.WHITE);
        final Map<Position, Piece> whitePieces = chessBoard.getPiecesWithPositionBy(Color.BLACK);

        return new ScoresDto(ScoreCalculator.calculate(blackPieces), ScoreCalculator.calculate(whitePieces));
    }
}
