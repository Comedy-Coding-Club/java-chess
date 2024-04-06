package chess.controller;

public enum State {
    RUNNING, END;

    public boolean isRunning() {
        return this == RUNNING;
    }
}
