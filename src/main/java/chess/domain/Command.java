package chess.domain;

import java.util.Arrays;

public enum Command {

    START("start", 1),
    CONTINUE("continue", 1),
    END("end", 1),
    MOVE("move", 3),
    STATUS("status", 1);

    private final String message;
    private final int argumentCount;

    Command(final String message, final int argumentCount) {
        this.message = message;
        this.argumentCount = argumentCount;
    }

    public static Command from(final String input) {
        validateBlank(input);

        if (input.startsWith("move")) {
            return MOVE;
        }
        return Arrays.stream(values())
                .filter(command -> command.message.equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다."));
    }

    public boolean matchArgumentCount(final int other) {
        return argumentCount == other;
    }

    private static void validateBlank(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    public String getMessage() {
        return message;
    }
}
