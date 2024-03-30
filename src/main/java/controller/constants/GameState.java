package controller.constants;

public enum GameState {
    NOT_STARTED,
    RUNNING,
    STOPPED,
    CHECKMATE,
    ;

    public boolean isNotStarted() {
        return equals(NOT_STARTED);
    }

    public boolean isRunning() {
        return equals(RUNNING);
    }
}
