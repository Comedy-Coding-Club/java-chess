package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public interface ChessGame {

    boolean isEnd();

    ChessGame startGame(Supplier<Boolean> checkRestart);

    ChessGame endGame();

    ChessGame move(Location source, Location target);

    Board getBoard();

    Score getScore(Color color);

    Color getWinner();

    Color getTurn();
}
