package domain.game;

import controller.constants.GameState;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;

public class ChessBoard {
    private final Turn turn;
    private final Map<Position, Piece> piecePosition;

    public ChessBoard(final Map<Position, Piece> piecePosition) {
        this.turn = new Turn(Color.WHITE);
        this.piecePosition = piecePosition;
    }

    // TODO: 컨트롤러 레벨에 있는 GameState가 도메인의 반환 타입을 사용되고 있다.
    public GameState move(final Position source, final Position target) {
        validateMovement(source, target);
        GameState gameState = update(source, target); // TODO: update와 move가 GameState를 반환하는 게 자연스럽나?
        turn.change(); // TODO: 게임이 끝났는데 turn.change()하는 게 무슨 소용일까?
        return gameState;
    }

    private void validateMovement(final Position source, final Position target) {
        validateSourceExists(source);

        Piece sourcePiece = piecePosition.get(source);
        validateCorrectTurn(sourcePiece);

        validateDifferentSourceTarget(source, target);
        validateOpponentTarget(source, target);

        sourcePiece.validateMovableRoute(source, target, piecePosition);
    }

    private void validateOpponentTarget(final Position source, final Position target) {
        if (hasSameColorPiece(source, target)) {
            throw new IllegalArgumentException("[ERROR] 같은 진영의 기물이 있는 곳으로 옮길 수 없습니다.");
        }
    }

    private boolean hasSameColorPiece(final Position source, final Position target) {
        Piece sourcePiece = piecePosition.get(source);

        if (piecePosition.containsKey(target)) {
            Piece targetPiece = piecePosition.get(target);
            return sourcePiece.isEqualColor(targetPiece.getColor());
        }
        return false;
    }

    private void validateSourceExists(final Position source) {
        if (!piecePosition.containsKey(source)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 Piece가 존재하지 않습니다.");
        }
    }

    private void validateDifferentSourceTarget(final Position source, final Position target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로의 이동입니다. 다시 입력해주세요.");
        }
    }

    private void validateCorrectTurn(final Piece sourcePiece) {
        if (turn.isNotTurn(sourcePiece)) {
            throw new IllegalArgumentException("[ERROR] 현재는 " + turn.getName() + "의 이동 차례입니다.");
        }
    }

    private GameState update(final Position source, final Position target) {
        Piece sourcePiece = piecePosition.get(source);
        // TODO: instaceof를 사용하지 않기 위해 Piece에게 너 킹이니? 가 아니라 게임이 끝났니 ? 라고 물어봐도 괜찮나?
        GameState gameState = checkGameEnds(target);
        piecePosition.put(target, sourcePiece);
        piecePosition.remove(source);
        return gameState;
    }

    private GameState checkGameEnds(final Position target) {
        if (isCheckmateWhenTargetPieceCaptured(target)) {
            return GameState.CHECKMATE;
        }
        return GameState.RUNNING;
    }

    private boolean isCheckmateWhenTargetPieceCaptured(final Position target) {
        return hasPiece(target) && findPieceByPosition(target).doesGameEndsWhenCaptured();
    }

    public boolean hasPiece(final Position position) {
        return piecePosition.containsKey(position);
    }

    public Piece findPieceByPosition(final Position position) {
        return piecePosition.get(position);
    }

    public double calculateScore(final Color color) {
        double score = 0;
        for (Map.Entry<Position, Piece> entry : piecePosition.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            if (piece.isEqualColor(color)) {
                score += piece.score(position, piecePosition);
            }
        }
        return score;
    }
}
