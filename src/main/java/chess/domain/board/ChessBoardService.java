package chess.domain.board;

import chess.domain.ChessGameService;
import chess.domain.Piece;
import chess.domain.position.Position;
import java.util.Collections;
import java.util.Map;

/**
 * 체스 보드에 관련된 책임
 */
public class ChessBoardService {

    private final BoardRepository boardRepository;

    public ChessBoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void initNewBoard(Map<Position, Piece> board) {
        clearBoard();
        board.forEach(boardRepository::placePiece);
    }

    public void clearBoard() {
        boardRepository.clearBoard();
    }

    public void movePiece(Position from, Position to) {
        Piece piece = boardRepository.findPieceByPosition(from);
        boardRepository.removePiece(from);
        boardRepository.placePiece(to, piece);
    }

    public Piece findPieceByPosition(Position position) {
        return boardRepository.findPieceByPosition(position);
    }

    public boolean hasPiece(Position position) {
        return boardRepository.hasPiece(position);
    }

    public boolean isEmptySpace(Position position) {
        return !hasPiece(position);
    }

    public boolean hasTwoKing() {
        Map<Position, Piece> board = boardRepository.getBoard();
        int kingCount = (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
        return kingCount == ChessGameService.DEFAULT_KING_COUNT;
    }

    public boolean isFirstGame() {
        return getBoard().isEmpty();
    }

    public Map<Position, Piece> getBoard() {
        return Collections.unmodifiableMap(boardRepository.getBoard());
    }
}
