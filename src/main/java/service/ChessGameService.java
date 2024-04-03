package service;

import domain.game.Turn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.Map;
import repository.PiecePositionRepository;
import repository.TurnRepository;

public class ChessGameService {
    private final PiecePositionRepository piecePositionRepository = new PiecePositionRepository();
    private final TurnRepository turnRepository = new TurnRepository();

    public void saveAllPiecePositions(final Map<Position, Piece> piecePosition) {
        piecePositionRepository.saveAll(piecePosition);
    }

    public void updatePiecePosition(final Position source, final Position target) {
        piecePositionRepository.deleteByPosition(target); // todo: TARGET이 없으면 에러뜸?
        piecePositionRepository.updatePosition(source, target);
    }

    public Map<Position, Piece> findAllPiecePositions() {
        return piecePositionRepository.findAllPiecePositions();
    }

    public void clearPiecePosition() {
        piecePositionRepository.clear();
    }

    public Turn findTurn() {
        return turnRepository.find();
    }
}
