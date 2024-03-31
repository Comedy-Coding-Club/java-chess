package chess.repository.entity;

import chess.service.domain.piece.Color;

public class Game {
    private final int gameId;
    private final Color turn;

    public Game(int gameId, Color turn) {
        this.gameId = gameId;
        this.turn = turn;
    }

    public int getGameId() {
        return gameId;
    }

    public Color getTurn() {
        return turn;
    }
}
