package chess.domain.command;

import java.util.Arrays;

public enum CommandType {

    RELOAD("reload"),
    START("start"),
    END("end"),
    STATUS("status"),
    MOVE("move");

    private final String message;

    CommandType(final String message) {
        this.message = message;
    }

    public static CommandType from(final String message) {
        return Arrays.stream(values())
                .filter(command -> command.message.equals(message))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 올바르지 않은 명령어입니다."));
    }

    public boolean isReload() {
        return this == CommandType.RELOAD;
    }

    public boolean isStart() {
        return this == CommandType.START;
    }

    public boolean isMove() {
        return this == CommandType.MOVE;
    }

    public boolean isEnd() {
        return this == CommandType.END;
    }

    public boolean isStatus() {
        return this == CommandType.STATUS;
    }
}
