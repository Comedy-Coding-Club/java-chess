package chess.domain.dbUtils;

import chess.domain.Piece;
import chess.domain.position.Position;

public record BoardDto(Position position, Piece piece) { // TODO 실제 DB 구조랑 동일하게 받아야하나?? 동일하게 받으면 테스트 할 때, 불편함. 매번 new Positiosn, new Piece 를 해줘야함
}
