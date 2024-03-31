package repository.mapper;

import controller.constants.GameState;
import java.util.Arrays;

public enum GameStateMapper {
    NOT_STARTED("NOT_STARTED", GameState.NOT_STARTED),
    RUNNING("RUNNING", GameState.RUNNING),
    STOPPED("STOPPED", GameState.STOPPED),
    CHECKMATE("CHECKMATE", GameState.CHECKMATE),
    ;

    private final String name;
    private final GameState gameState;

    GameStateMapper(final String name, final GameState gameState) {
        this.name = name;
        this.gameState = gameState;
    }

    public static GameState getGameStateByName(String name) {
        return Arrays.stream(GameStateMapper.values())
                .filter(element -> element.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] GameState 객체를 생성할 수 없습니다."))
                .gameState;
    }
}
