package chess.domain.board;

import chess.domain.Piece;
import chess.domain.dbUtils.BoardDao;
import chess.domain.dbUtils.BoardDto;
import chess.domain.position.Position;
import java.util.Map;

public class DBChessBoard implements ChessBoard{

    private final BoardDao boardDao;

    public DBChessBoard(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void initBoard() {
        boardDao.clearAllPieces();
        Map<Position, Piece> board = DefaultInitializer.initializer();
        board.entrySet()
                .stream().map(entry -> new BoardDto(entry.getKey(), entry.getValue()))
                .forEach(boardDao::create);
    }

    @Override
    public void putPiece(Position position, Piece piece) {
        if (hasPiece(position)) {
            boardDao.delete(position);
        }
        boardDao.create(new BoardDto(position, piece));
    }

    @Override
    public void movePiece(Position from, Position to) {
        Piece piece = findPieceByPosition(from);
        boardDao.delete(from);
        putPiece(to, piece);
    }

    @Override
    public boolean hasPiece(Position position) {
        return boardDao.findByPosition(position).isPresent();
    }

    @Override
    public Piece findPieceByPosition(Position position) {
        BoardDto boardDto = boardDao.findByPosition(position)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
        return boardDto.piece();
    }

    @Override
    public boolean isEmptySpace(Position position) {
        return !hasPiece(position);
    }

    @Override
    public boolean hasKing(int count) {
        Map<Position, Piece> board = getBoard();
        int kingCount = (int) board.values()
                .stream()
                .filter(Piece::isKing)
                .count();
        return kingCount == count;
    }

    @Override
    public Map<Position, Piece> getBoard() {
        return boardDao.findAllPieces();
    }
}
