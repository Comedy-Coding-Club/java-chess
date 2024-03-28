package chess.domain.dbUtils;

import chess.domain.Color;
import chess.domain.Piece;
import chess.domain.PieceType;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;

public record BoardDto(Position position, Piece piece) { // TODO 실제 DB 구조랑 동일하게 받아야하나?? 동일하게 받으면 테스트 할 때, 불편함. 매번 new Positiosn, new Piece 를 해줘야함

    public static BoardDto createByEntity(String pieceTypeValue, int columnIndex, int rowIndex, String colorValue) {
        return new BoardDto(
                new Position(Row.findByIndex(rowIndex), Column.findByIndex(columnIndex)),
                new Piece(PieceType.findByName(pieceTypeValue), Color.findByName(colorValue))
        );
    }
}
