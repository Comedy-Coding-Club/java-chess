package chess.db;

import chess.domain.Piece;
import chess.dto.BoardPieceDTO;
import chess.domain.board.BoardRepository;
import chess.domain.position.Position;
import java.util.Map;

public class DBBoardRepository implements BoardRepository {

    private final BoardDAO boardDao;

    public DBBoardRepository(BoardDAO boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void placePiece(Position position, Piece piece) {
        boardDao.create(new BoardPieceDTO(position, piece));
    }

    @Override
    public void removePiece(Position position) {
        boardDao.delete(position);
    }

    @Override
    public void clearBoard() {
        boardDao.clearAllPieces();
    }

    @Override
    public boolean hasPiece(Position position) {
        return boardDao.findByPosition(position).isPresent();
    }

    @Override
    public Piece findPieceByPosition(Position position) {
        BoardPieceDTO boardPieceDto = boardDao.findByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
        return boardPieceDto.piece();
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return boardDao.findAllPieces().board();
    }
}
