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

    public ChessGame loadChessGame() {
        if (chessGameRepository.isGameExist()) {
            GameState gameState = chessGameRepository.findGameStatusById();
            Color color = chessGameRepository.findColorById();
            ChessBoard chessBoard = chessBoardRepository.findByChessGameId();
            return new ChessGame(chessBoard, gameState, color);
        }
        ChessGame chessGame = new ChessGame();
        chessBoardRepository.save(chessGame.getBoard());
        chessGameRepository.save(chessGame.getColor(), chessGame.getGameState());
        return chessGame;
    }

    public void updateChessGame(ChessGame chessGame) {
        chessBoardRepository.delete();
        chessBoardRepository.save(chessGame.getBoard());
        chessGameRepository.updateGameStatus(chessGame.getGameState());
        chessGameRepository.updateColor(chessGame.getColor());
    }
}
