package repository;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.Rook;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecePositionRepositoryTest {
    private final PiecePositionRepository repository = new PiecePositionRepository();

    @DisplayName("체스판의 위치와 기물의 정보를 데이터베이스에 저장한다.")
    @Test
    void savePiecePosition() {
        Position position = new Position(new File('a'), new Rank(1));
        Piece piece = new Piece(Rook.create(), Color.BLACK);

        int rows = repository.save(position, piece);

        Assertions.assertThat(rows).isEqualTo(1);
    }
}
