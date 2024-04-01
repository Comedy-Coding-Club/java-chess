package domain.game;

import controller.constants.GameState;
import domain.piece.Color;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import repository.PiecePositionRepository;
import repository.TurnRepository;

public class ChessBoard {
    private final Turn turn;
    private final Map<Position, Piece> piecePosition;
    private final PiecePositionRepository piecePositionRepository;
    private final TurnRepository turnRepository;

    public ChessBoard() {
        this.turnRepository = new TurnRepository();
        this.turn = turnRepository.find();
        this.turnRepository.save(turn);

        this.piecePositionRepository = new PiecePositionRepository();
        this.piecePosition = piecePositionRepository.findAllPiecePositions();
    }

    protected ChessBoard(final Map<Position, Piece> piecePosition) {
        this.turnRepository = new TurnRepository();
        this.turn = new Turn(Color.WHITE);
        this.turnRepository.save(turn);

        this.piecePosition = piecePosition;
        this.piecePositionRepository = new PiecePositionRepository();
    }

    public GameState move(final Position source, final Position target) {
        validateMovement(source, target);
        GameState gameState = update(source, target);
        turn.changeTurn();
        turnRepository.save(turn);

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
        GameState gameState = checkGameEnds(target);

        Piece sourcePiece = piecePosition.get(source);
        piecePosition.put(target, sourcePiece);
        piecePosition.remove(source);

        updatePiecePosition(source, target);
        return gameState;
    }

    private void updatePiecePosition(final Position source, final Position target) {
        if (piecePosition.containsKey(target)) {
            piecePositionRepository.deleteByPosition(target);
        }
        piecePositionRepository.updatePosition(source, target);
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

    public void saveChessBoard() {
        clear();
        for (Map.Entry<Position, Piece> entry : piecePosition.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            piecePositionRepository.save(position, piece);
        }
    }

    public void clear() {
        piecePositionRepository.clear();
    }
}
