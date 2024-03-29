package domain.game;

public enum GameState {
    READY,
    RUNNING,
    END;

    public boolean isRunning() {
        return this == RUNNING;
    }

    public boolean isNotRunning() {
        return !isRunning();
    }

    public boolean isEnd() {
        return this == END;
    }

    public boolean isNotEnd() {
        return !isEnd();
    }
}
