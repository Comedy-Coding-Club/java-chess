package repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.Rook;
import domain.position.File;
import domain.position.Position;
import domain.position.Rank;
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

        assertThat(rows).isEqualTo(1);
    }

    @DisplayName("체스판의 위치를 기준으로 기물을 조회한다.")
    @Test
    void findPieceByPosition() {
        Position position = new Position(new File('a'), new Rank(1));
        Piece piece = new Piece(Rook.create(), Color.BLACK);
        repository.save(position, piece);

        Piece savedPiece = repository.findPieceByPosition(position);

        assertThat(savedPiece).isEqualTo(piece);
    }

    @DisplayName("기물의 체스판 위치 정보를 모두 제거한다.")
    @Test
    void clear() {
        Position position = new Position(new File('b'), new Rank(1));
        Piece piece = new Piece(Rook.create(), Color.BLACK);
        repository.save(position, piece);

        repository.clear();

        assertThatThrownBy(() -> repository.findPieceByPosition(position))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("기물의 위치 정보를 삭제한다.")
    @Test
    void delete() {
        Position position = new Position(new File('b'), new Rank(1));
        Piece piece = new Piece(Rook.create(), Color.BLACK);
        repository.save(position, piece);

        repository.deleteByPosition(position);

        assertThatThrownBy(() -> repository.findPieceByPosition(position))
                .isInstanceOf(RuntimeException.class);
    }
}
