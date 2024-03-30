package chess.domain.chessGame;

import chess.domain.board.Board;
import chess.domain.location.Location;
import chess.domain.piece.Color;
import chess.domain.piece.Score;
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
