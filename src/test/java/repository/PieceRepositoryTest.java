package repository;

import static org.assertj.core.api.Assertions.assertThat;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.Rook;
import java.sql.Connection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceRepositoryTest {
    private final PieceRepository pieceRepository = new PieceRepository();

    @DisplayName("드라이버 연결 테스트")
    @Test
    void connectionTest() {
        final Connection connection = pieceRepository.getConnection();
        assertThat(connection).isNotNull();
    }

    @DisplayName("기물의 정보를 데이터베이스에 저장한다.")
    @Test
    void saveTest() {
        Piece piece = new Piece(Rook.create(), Color.BLACK);

        int savedRows = pieceRepository.save(piece);

        assertThat(savedRows).isEqualTo(1);
    }

    @DisplayName("기물의 ID를 기준으로 기물을 조회한다.")
    @Test
    void findByPieceIdTest() {
        Piece piece = new Piece(Rook.create(), Color.BLACK);
        pieceRepository.save(piece);

        //  TODO: 불안정한 테스트
        assertThat(pieceRepository.findByPieceId("1")).isEqualTo(piece);
    }
}
