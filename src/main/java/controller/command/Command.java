package controller.command;

import controller.ChessGame;
import java.util.Arrays;
import java.util.function.Function;
import view.OutputView;
import view.command.CommandType;

public enum Command {
    START(CommandType.START, StartCommandExecutor::new),
    END(CommandType.END, EndCommandExecutor::new),
    MOVE(CommandType.MOVE, MoveCommandExecutor::new),
    STATUS(CommandType.STATUS, StatusCommandExecutor::new),
    CONTINUE(CommandType.CONTINUE, ContinueCommandExecutor::new),
    ;

    private final CommandType commandType;
    private final Function<CommandType, CommandExecutor> executorFunction;

    Command(
            final CommandType commandType,
            final Function<CommandType, CommandExecutor> executorFunction
    ) {
        this.commandType = commandType;
        this.executorFunction = executorFunction;
    }

    public static Command from(final CommandType commandType) {
        return Arrays.stream(Command.values())
                .filter(command -> command.commandType == commandType)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void execute(final OutputView outputView, final ChessGame chessGame) {
        executorFunction.apply(commandType).execute(outputView, chessGame);
    }
}
