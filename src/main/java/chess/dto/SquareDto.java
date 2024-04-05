package chess.dto;

import chess.domain.PieceFactory;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Position;
import chess.domain.piece.type.Bishop;
import chess.domain.piece.type.BlackPawn;
import chess.domain.piece.type.Empty;
import chess.domain.piece.type.King;
import chess.domain.piece.type.Knight;
import chess.domain.piece.type.Queen;
import chess.domain.piece.type.Rook;
import chess.domain.piece.type.WhitePawn;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record SquareDto(String position, PieceType pieceType, Color color) {
    public static List<SquareDto> toSquareDtos(final Map<Position, Piece> chessBoard) {
        final List<SquareDto> squareDtos = new ArrayList<>();

        chessBoard.forEach((rawPosition, rawPiece) -> {
                    final String position = rawPosition.getFile() + rawPosition.getRank();
                    final PieceType pieceType = rawPiece.getPieceType();
                    final Color color = rawPiece.getColor();

                    squareDtos.add(new SquareDto(position, pieceType, color));
                }
        );
        return squareDtos;
    }

    public static Map<Position, Piece> toChessBoard(final List<SquareDto> squareDtos) {
        return squareDtos.stream()
                .collect(Collectors.toMap(
                        squareDto -> Position.from(squareDto.position()),
                        squareDto -> PieceFactory.of(squareDto.pieceType(), squareDto.color()),
//                        squareDto -> convertToPiece(squareDto.pieceType(), squareDto.color()),
                        (o1, o2) -> o1,
                        LinkedHashMap::new
                ));
    }

    private static Piece convertToPiece(final PieceType pieceType, final Color color) {
        if (pieceType == PieceType.KING) {
            return new King(color);
        }
        if (pieceType == PieceType.QUEEN) {
            return new Queen(color);
        }
        if (pieceType == PieceType.ROOK) {
            return new Rook(color);
        }
        if (pieceType == PieceType.BISHOP) {
            return new Bishop(color);
        }
        if (pieceType == PieceType.KNIGHT) {
            return new Knight(color);
        }
        if (pieceType == PieceType.PAWN && color == Color.WHITE) {
            return new WhitePawn();
        }
        if (pieceType == PieceType.PAWN && color == Color.BLACK) {
            return new BlackPawn();
        }
        return new Empty();
    }
}
