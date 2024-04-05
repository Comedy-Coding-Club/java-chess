package chess.dto;

import chess.domain.Command;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandPosition {

    public static final int MOVE_COMMAND_PART_COUNT = 3;

    private final Command command;
    private final String source;
    private final String target;

    public CommandPosition(final Command command, final String source, final String target) {
        this.command = command;
        this.source = source;
        this.target = target;
    }

    public CommandPosition(final Command command) {
        this.command = command;
        this.source = "";
        this.target = "";
    }

    public static CommandPosition from(final String input) {
        Command command = Command.from(input);

        if (command == Command.MOVE) {
            List<String> splits = Arrays.asList(input.split(" "));
            validateMoveCommandPartCount(splits);
            if (!command.matchArgumentCount(splits.size())){
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
            }
            String source = splits.get(1);
            String target = splits.get(2);
            return new CommandPosition(Command.MOVE, source, target);
        }

        return new CommandPosition(Command.from(input));
    }

    public boolean isCommand(final Command other) {
        return command == other;
    }

    private static void validateMoveCommandPartCount(final List<String> splittedInput) {
        if (splittedInput.size() != MOVE_COMMAND_PART_COUNT) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommandPosition that = (CommandPosition) o;
        return command == that.command && Objects.equals(source, that.source) && Objects.equals(target,
                that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, source, target);
    }
}
