package domain.game;

import static fixture.PositionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import domain.piece.ChessBoardGenerator;
import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.Score;
import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("source에 위치한 piece를 target으로 이동한다.")
    @Test
    void movePieceToTarget() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        chessBoard.move(B2, B3);

        assertAll(
                () -> assertThat(chessBoard.isNotEmptyAt(B3)).isTrue(),
                () -> assertThat(chessBoard.isNotEmptyAt(B3)).isTrue()
        );
    }

    @DisplayName("source에 piece가 없다면 에러를 반환한다.")
    @Test
    void movePieceIfSourceHasNotPiece() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(B3, B4, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("옮기고자 하는 위치에 같은 진영의 Piece가 있다면 에러를 반환한다.")
    @Test
    void hasSameColorPiece() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(A1, A2, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("같은 위치로의 이동이라면 에러를 반환한다.")
    @Test
    void moveToSamePosition() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(B1, B1, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("앞에 다른 진영의 기물이 있는 경우 폰이 이동하지 못한다.")
    @Test
    void movePawnWhenFrontPositionHasOtherPiece() {
        Position whiteSourcePosition = B2;
        Position whiteTargetPosition = B4;

        Position blackSourcePosition = B7;
        Position balckTargetPosition = B5;

        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        chessBoard.move(whiteSourcePosition, whiteTargetPosition);
        chessBoard.move(blackSourcePosition, balckTargetPosition);

        assertThatThrownBy(() -> chessBoard.checkRoute(whiteTargetPosition, balckTargetPosition, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("대각선에 다른 진영의 기물이 있는 경우 폰이 이동할 수 있다.")
    @Test
    void movePawnWhenDiagonalPositionHasOtherPiece() {
        Position whiteSourcePosition = B2;
        Position whiteTargetPosition = B4;

        Position blackSourcePosition = C7;
        Position balckTargetPosition = C5;

        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        chessBoard.move(whiteSourcePosition, whiteTargetPosition);
        chessBoard.move(blackSourcePosition, balckTargetPosition);

        assertThatCode(() -> chessBoard.checkRoute(whiteTargetPosition, balckTargetPosition, Color.WHITE))
                .doesNotThrowAnyException();
    }

    @DisplayName("나이트를 제외한 기물은 이동하는 경로에 기물이 있으면 이동하지 못한다.")
    @Test
    void isOverlappedPath() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        assertThatThrownBy(() -> chessBoard.checkRoute(A1, A3, Color.WHITE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("나이트는 이동하는 경로에 기물이 있어도 이동할 수 있다.")
    @Test
    void knightCanJump() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();
        assertThatCode(() -> chessBoard.checkRoute(B1, C3, Color.WHITE))
                .doesNotThrowAnyException();
    }

    @DisplayName("흑색 킹이 잡히면 게임이 끝난다.")
    @Test
    void blackKingDeath() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        // 흑 승리 기보
        chessBoard.move(F2, F3);
        chessBoard.move(E7, E5);
        chessBoard.move(G2, G4);
        chessBoard.move(D8, H4);
        chessBoard.move(H2, H3);
        chessBoard.move(H4, E1);

        assertThat(chessBoard.isKingDeath()).isTrue();
    }

    @DisplayName("백색 킹이 잡히면 게임이 끝난다.")
    @Test
    void whiteKingDeath() {
        ChessBoard chessBoard = ChessBoardGenerator.generateInitialChessBoard();

        // 백 승리 기보
        chessBoard.move(E2, E3);
        chessBoard.move(F7, F6);
        chessBoard.move(D1, H5);
        chessBoard.move(G8, H6);
        chessBoard.move(H5, E8);

        assertThat(chessBoard.isKingDeath()).isTrue();
    }

    @DisplayName("기물의 점수를 계산한다.")
    @Test
    void calculateScore() {

        Map<Position, Piece> piecePositions = new HashMap<>();

        piecePositions.put(B8, new Piece(new King(), Color.BLACK));
        piecePositions.put(C8, new Piece(new Rook(), Color.BLACK));
        piecePositions.put(A7, new Piece(new BlackPawn(), Color.BLACK));
        piecePositions.put(C7, new Piece(new BlackPawn(), Color.BLACK));
        piecePositions.put(D7, new Piece(new Bishop(), Color.BLACK));
        piecePositions.put(B6, new Piece(new BlackPawn(), Color.BLACK));
        piecePositions.put(E6, new Piece(new Queen(), Color.BLACK));
        piecePositions.put(F4, new Piece(new Knight(), Color.WHITE));
        piecePositions.put(G4, new Piece(new Queen(), Color.WHITE));
        piecePositions.put(F3, new Piece(new WhitePawn(), Color.WHITE));
        piecePositions.put(H3, new Piece(new WhitePawn(), Color.WHITE));
        piecePositions.put(F2, new Piece(new WhitePawn(), Color.WHITE));
        piecePositions.put(G2, new Piece(new WhitePawn(), Color.WHITE));
        piecePositions.put(E1, new Piece(new Rook(), Color.WHITE));
        piecePositions.put(F1, new Piece(new King(), Color.WHITE));

        /*
         [기물 배치]
         .KR.....  8
         P.PB....  7
         .P..Q...  6
         ........  5
         .....nq.  4
         .....p.p  3
         .....pp.  2
         ....rk..  1
         abcdefgh
         */
        ChessBoard chessBoard = new ChessBoard(piecePositions);

        ScoreBoard scoreBoard = chessBoard.calculateScore();
        Map<Color, Score> board = scoreBoard.getBoard();

        assertAll(
                () -> assertThat(board).containsEntry(Color.BLACK, new Score(20)),
                () -> assertThat(board).containsEntry(Color.WHITE, new Score(19.5))
        );
    }
}
