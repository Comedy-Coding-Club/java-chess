package domain.game;

import java.util.Arrays;

public enum GameState {
    READY,
    RUNNING,
    END;

    public static GameState of(String gameState) {
        return Arrays.stream(values())
                .filter(state -> state.name().equalsIgnoreCase(gameState))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게임 상태입니다."));
    }

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
