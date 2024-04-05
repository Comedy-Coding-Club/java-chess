//package chess.dao;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import chess.domain.piece.Color;
//import chess.domain.piece.File;
//import chess.domain.piece.Piece;
//import chess.domain.piece.PieceType;
//import chess.domain.piece.Position;
//import chess.domain.piece.Rank;
//import chess.domain.piece.type.Bishop;
//import chess.entity.SquareDto;
//import java.util.List;
//import java.util.Map;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//class SquareDaoTest {
//
//    private final SquareDao squareDao = new SquareDao();
//
//    @DisplayName("보드칸들을 정상적으로 불러오는지 확인한다.")
//    @Test
//    void findSquares() {
//        // given && when
//        final List<SquareDto> squares = squareDao.findSquares("1");
//
//        // then
//        assertThat(squares).containsExactlyInAnyOrder(
//                new SquareDto("a1", PieceType.ROOK, Color.WHITE),
//                new SquareDto("a2", PieceType.KNIGHT, Color.WHITE)
//        );
//    }
//
//    @DisplayName("보드칸들이 정상적으로 추가되는지 확인한다.")
//    @Test
//    void addSquares() {
//        // given && when
//        Map<Position, Piece> pieces = Map.of(new Position(File.A, Rank.THREE), new Bishop(Color.WHITE));
//        squareDao.addSquares(pieces, "1");
//        final List<SquareDto> squares = squareDao.findSquares("1");
//
//        // then
//        assertThat(squares).contains(new SquareDto("a3", PieceType.BISHOP, Color.WHITE));
//    }
//
//    @DisplayName("해당 게임아이디를 가진 보드칸들이 정상적으로 삭제되는지 확인한다.")
//    @Test
//    void deleteSquares() {
//        // given && when
//        squareDao.deleteSquares("1");
//        final List<SquareDto> squares = squareDao.findSquares("1");
//
//        // then
//        assertThat(squares).isEmpty();
//    }
//}
