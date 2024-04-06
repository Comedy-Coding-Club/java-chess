package chess.service;

import chess.dao.BoardDao;
import chess.dao.SquareDao;
import chess.domain.board.ChessBoard;
import chess.domain.position.Position;
import chess.dto.BoardDto;
import chess.domain.command.Command;
import chess.dto.PieceDto;
import chess.dto.PositionDto;
import chess.dto.SquaresDto;
import chess.util.ChessBoardInitializer;
import java.util.List;

public class ChessService {

    private final SquareDao squareDao;
    private final BoardDao boardDao;

    public ChessService(final SquareDao squareDao, final BoardDao boardDao) {
        this.squareDao = squareDao;
        this.boardDao = boardDao;
    }

    public ChessBoard createBoard() {
        final ChessBoard chessBoard = ChessBoardInitializer.init();
        final BoardDto boardDto = BoardDto.fromChessBoard(chessBoard);

        final int boardId = boardDao.insertBoard(boardDto);
        chessBoard.setId(boardId);

        createAllSquares(chessBoard);
        return chessBoard;
    }

    public void move(final Command command, final ChessBoard chessBoard) {
        final List<String> body = command.getBody();

        final Position current = new Position(body.get(0));
        final Position destination = new Position(body.get(1));

        chessBoard.move(current, destination);

        final BoardDto boardDto = BoardDto.fromChessBoard(chessBoard);
        squareDao.deleteAllSquares(boardDto);
        createAllSquares(chessBoard);
        boardDao.updateTurn(boardDto);
    }

    public ChessBoard findRecentBoard() {
        final BoardDto boardDto = boardDao.findRecentBoard();
        final SquaresDto squaresDto = squareDao.findById(boardDto.id());

        return boardDto.toChessBoard(squaresDto);
    }

    private void createAllSquares(final ChessBoard chessBoard) {
        chessBoard.getPieces().forEach((key, value)
                -> squareDao.insertSquare(chessBoard.getId(), PositionDto.fromPosition(key), PieceDto.fromPiece(value)));
    }
}
