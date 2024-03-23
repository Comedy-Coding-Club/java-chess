package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.piece.Position;
import chess.domain.piece.type.Empty;
import chess.domain.piece.type.Pawn;
import java.util.List;
import java.util.Map;

public class ChessBoard {

    //    private Set<Piece> pieces;
    private Map<Position, Piece> pieces; // map<position, piece>으로 갖고있을 수 있을듯

    public ChessBoard(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(final List<String> positions) {
        final String sourcePosition = positions.get(0);
        final String targetPosition = positions.get(1);

        move2(Position.from(sourcePosition), Position.from(targetPosition));
    }

//    void move(final Position sourcePosition, final Position targetPosition) {
//        final Piece sourcePiece = findPieceBy(sourcePosition);
//
//        if (sourcePiece.isClass(Pawn.class) && canPawnCatch(sourcePiece, targetPosition)) {
//            catchPiece(sourcePosition, targetPosition);
//            return;
//        }
//
//        validateStrategy(sourcePiece, targetPosition);
//        validateJumpOver(sourcePiece, targetPosition);
//
//        if (isPieceExist(targetPosition)) {
//            validateNotMySide(sourcePiece, targetPosition);
//            catchPiece(sourcePiece, targetPosition);
//        }
//
//        sourcePiece.move(targetPosition);
//    }

    void move2(final Position sourcePosition, final Position targetPosition) {
        final Piece sourcePiece = findPieceBy(sourcePosition);

        if (sourcePiece.isClass(Pawn.class) && canPawnCatch(sourcePosition, targetPosition)) {
            catchPiece(sourcePosition, targetPosition);
            return;
        }

        validateStrategy(sourcePosition, targetPosition);
        validateJumpOver(sourcePosition, targetPosition);

        if (isPieceExist(targetPosition)) {
            validateNotMySide(sourcePiece, targetPosition);
            catchPiece(sourcePosition, targetPosition);
        }

        pieces.put(targetPosition, sourcePiece);
    }

    Piece findPieceBy(final Position input) {
        if (isPieceExist(input)) {
            return pieces.get(input);
        }
        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
//        return pieces.stream()
//                .filter(piece -> piece.isPosition(input))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다."));
    }

    private boolean isPieceExist(final Position input) {
        boolean b = !pieces.get(input).isClass(Empty.class);
        return b;
    }

    private boolean canPawnCatch(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);
        Piece targetPiece = pieces.get(targetPosition);
        MultiDirection multiDirection = MultiDirection.of(sourcePosition, targetPosition);

        if (!isPieceExist(targetPosition)) {
            return false;
        }
        return ((multiDirection == MultiDirection.LEFT_DIAGONAL || multiDirection == MultiDirection.RIGHT_DIAGONAL)
                && (sourcePosition.getRankDistance(targetPosition) == Pawn.DEFAULT_STEP))
                && !sourcePiece.isMySide(targetPiece);
//        return ((sourcePosition.getPosition().isDiagonalWith(targetPosition)
//                && sourcePosition.getPosition().getRankDistance(targetPosition) == Pawn.DEFAULT_STEP))
//                && !sourcePosition.isMySide(findPieceBy(targetPosition));
    }
//    private boolean isPieceExist(final Position input) {
//        return pieces.stream().anyMatch(piece -> piece.isPosition(input));
//    }

    private void catchPiece(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);

        pieces.put(targetPosition, sourcePiece);
        pieces.put(sourcePosition, new Empty());
//        pieces = removePiece(targetPosition);
//        sourcePiece.move(targetPosition);
    }

//    private Set<Piece> removePiece(final Position targetPosition) {
//        return pieces.stream()
//                .filter(piece -> !piece.isPosition(targetPosition))
//                .collect(Collectors.toSet());
//    }

    private void validateStrategy(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = findPieceBy(sourcePosition);

        if (!sourcePiece.canMoveTo(sourcePosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 전략상 이동할 수 없는 위치입니다.");
        }
    }

    private void validateJumpOver(final Position sourcePosition, final Position targetPosition) {
        if (existPieceInWay(sourcePosition, targetPosition)) {
            throw new IllegalArgumentException("[ERROR] 경로상 기물이 존재합니다.");
        }
    }

    private boolean existPieceInWay(final Position sourcePosition, final Position targetPosition) {
        Piece sourcePiece = pieces.get(sourcePosition);

        return sourcePiece.getRoute(sourcePosition, targetPosition).stream()
                .anyMatch(this::isPieceExist);
    }

    private void validateNotMySide(final Piece sourcePiece, final Position targetPosition) {
        final Piece targetPiece = findPieceBy(targetPosition);

        if (sourcePiece.isMySide(targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 잡을 수 없는 기물입니다.");
        }
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    //    public Set<Piece> getPieces() {
//        return pieces;
//    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + pieces +
                '}';
    }
}
