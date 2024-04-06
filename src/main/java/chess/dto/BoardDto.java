package chess.dto;

import chess.domain.board.ChessBoard;
import chess.domain.board.Turn;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.stream.Collectors;

public record BoardDto(int id, String turn){

    public static BoardDto fromChessBoard(final ChessBoard chessBoard) {
        return new BoardDto(chessBoard.getId(), chessBoard.getTurn().name());
    }

    public ChessBoard toChessBoard(final SquaresDto squaresDto) {
        final Map<Position, Piece> pieces = squaresDto.squareDtos().stream()
                .collect(Collectors.toMap(SquareDto::toPosition, SquareDto::toPiece));

        return new ChessBoard(id, new Turn(Color.from(turn)), pieces);
    }
}
