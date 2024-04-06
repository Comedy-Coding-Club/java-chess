package chess.domain.command;

import java.util.Arrays;
import java.util.List;

public class Command {

    private final CommandType commandType;
    private final List<String> body;

    private Command(final CommandType commandType, final List<String> body) {
        this.commandType = commandType;
        this.body = body;
    }

    public static Command fromStart(final String value) {
        final CommandType commandType = CommandType.from(value);

        if (!(commandType.isStart() || commandType.isEnd() || commandType.isReload())) {
            throw new IllegalArgumentException("[ERROR] 시작 명령어는 start,end 혹은 reload입니다.");
        }

        return new Command(commandType, List.of());
    }

    public static Command fromPlay(final String value) {
        final List<String> splitCommand = splitCommand(value);
        final CommandType commandType = CommandType.from(splitCommand.get(0));

        if (commandType.isStart()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 명령어입니다.");
        }

        if (commandType.isMove()) {
            return new Command(commandType, getPositions(splitCommand));
        }

        return new Command(commandType, List.of());
    }

    private static List<String> getPositions(final List<String> command) {
        if (command.size() != 3) {
            throw new IllegalArgumentException("[ERROR] 움직일 위치를 작성해주세요.");
        }

        return command.subList(1, 3);
    }

    private static List<String> splitCommand(final String command) {
        return Arrays.asList(command.split(" "));
    }

    public boolean isReload() { return commandType.isReload(); }

    public boolean isStart() {
        return commandType.isStart();
    }

    public boolean isMove() {
        return commandType.isMove();
    }

    public boolean isEnd() {
        return commandType.isEnd();
    }

    public boolean isStatus() {
        return commandType.isStatus();
    }

    public List<String> getBody() {
        return body;
    }
}
