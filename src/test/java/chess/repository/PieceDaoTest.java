package chess.repository;

import static chess.domain.location.LocationFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import chess.domain.location.Location;
import chess.domain.location.LocationFixture;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.implement.King;
import chess.domain.piece.implement.Queen;
import chess.domain.piece.implement.WhitePawn;
import java.util.Locale;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

    private static final Map<Location, Piece> CUSTOM_BOARD = Map.of(
            A1, new WhitePawn(),
            B4, new King(Color.BLACK),
            F3, new Queen(Color.WHITE)
    );
    private static final PieceDao PIECE_DAO = new PieceDao();

    @DisplayName("저장 및 조회 테스트")
    @Test
    void saveFindTest() {
        PIECE_DAO.saveBoard(CUSTOM_BOARD);

        Assertions.assertThat(PIECE_DAO.loadBoard())
                .containsExactlyInAnyOrderEntriesOf(CUSTOM_BOARD);
    }
}
