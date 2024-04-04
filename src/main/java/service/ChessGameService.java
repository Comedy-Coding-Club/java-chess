package service;

import domain.game.ChessBoard;
import domain.game.ChessGame;
import domain.game.GameState;
import domain.piece.Color;
import repository.ChessBoardRepository;
import repository.ChessGameRepository;

public class ChessGameService {
    private final ChessBoardRepository chessBoardRepository;
    private final ChessGameRepository chessGameRepository;

    public ChessGameService(ChessBoardRepository chessBoardRepository, ChessGameRepository chessGameRepository) {
        this.chessBoardRepository = chessBoardRepository;
        this.chessGameRepository = chessGameRepository;
    }

    public void loadChessGame(ChessGame chessGame) {
        GameState gameState = chessGameRepository.findGameStatusById();
        Color color = chessGameRepository.findColorById();
        ChessBoard chessBoard = chessBoardRepository.findByChessGameId();

        if (gameState == null) {
            chessGameRepository.save(chessGame.getColor(), chessGame.getGameState());
            chessBoardRepository.save(chessGame.getBoard());
        }

        if (gameState != null) {
            chessGame.update(chessBoard, gameState, color);
        }
    }

    public void updateChessGame(ChessGame chessGame) {
        chessBoardRepository.delete();
        chessBoardRepository.save(chessGame.getBoard());
        chessGameRepository.updateGameStatus(chessGame.getGameState());
        chessGameRepository.updateColor(chessGame.getColor());
    }
}
