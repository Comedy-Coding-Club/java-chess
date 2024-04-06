package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.type.Pawn;
import chess.domain.position.Movement;
import chess.domain.position.Position;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessBoard {

    private int id;
    private final Turn turn;
    private final Map<Position, Piece> pieces;

    public ChessBoard(final int id, final Turn turn, final Map<Position, Piece> pieces) {
        this.id = id;
        this.turn = turn;
        this.pieces = pieces;
    }

    public void setId(int id) {
        if (this.id != 0) {
            throw new IllegalArgumentException("[ERROR] 보드에 새로운 id값을 할당할 수 없습니다.");
        }

        this.id = id;
    }

    public void move(final Position current, final Position destination) {
        final Piece currentPiece = findPieceBy(current);

        validateTurn(turn, currentPiece);
        validateStrategy(currentPiece, current, destination);
        validatePieceInRoute(getRoute(currentPiece, current, destination));

        movePiece(currentPiece, current, destination);

        if (!isKingCaught()) {
            turn.change();
        }
    }

    public Piece findPieceBy(final Position position) {
        if (isPieceExist(position)) {
            return pieces.get(position);
        }

        throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 존재하지 않습니다.");
    }

    public Map<Position, Piece> getPiecesWithPositionBy(final Color color) {
        return pieces.entrySet().stream()
                .filter(piece -> piece.getValue().isColor(color))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public boolean isKingCaught() {
        return pieces.values().stream().filter(Piece::isKing).count() < 2;
    }

    private void validateTurn(final Turn turn, final Piece currentPiece) {
        if (!turn.myTurn(currentPiece)) {
            throw new IllegalArgumentException(String.format("[ERROR] 현재는 %s의 차례입니다.", turn.getTurn()));
        }
    }

    private Set<Position> getRoute(final Piece currentPiece, final Position current, final Position destination) {
        final Movement movement = new Movement(current, destination);

        if (isPieceExist(destination)) {
            validateOpponent(currentPiece, findPieceBy(destination));
        }

        return currentPiece.getRoute(movement);
    }

    private void validateStrategy(final Piece currentPiece, final Position current, final Position destination) {
        final Movement movement = new Movement(current, destination);

        if (currentPiece.isPawn() && isPieceExist(destination) && ((Pawn) currentPiece).canCatch(movement)) {
            return;
        }

        if (!currentPiece.canMove(movement)) {
            throw new IllegalArgumentException("[ERROR] 전략상 이동할 수 없는 위치입니다.");
        }
    }

    private void validatePieceInRoute(final Set<Position> route) {
        final boolean existPiece = pieces.keySet().stream().anyMatch(route::contains);

        if (existPiece) {
            throw new IllegalArgumentException("[ERROR] 경로상 기물이 존재합니다.");
        }
    }

    private void validateOpponent(final Piece currentPiece, final Piece targetPiece) {
        if (!currentPiece.isOpponent(targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
    }

    private boolean isPieceExist(final Position position) {
        return pieces.containsKey(position);
    }

    private void movePiece(final Piece currentPiece, final Position current, final Position destination) {
        pieces.remove(current);
        pieces.put(destination, currentPiece);
    }

    public Map<Position, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }

    public int getId() {
        return id;
    }

    public Color getTurn() {
        return turn.getTurn();
    }
}
