package controller.command;

import domain.game.ChessGame;
import java.util.Arrays;
import java.util.function.Function;
import view.command.CommandType;

public enum Command {
    START(CommandType.START, StartCommandExecutor::new),
    END(CommandType.END, EndCommandExecutor::new),
    MOVE(CommandType.MOVE, MoveCommandExecutor::new),
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
                .orElseThrow(IllegalStateException::new);
    }

    public void execute(final ChessGame chessGame) {
        executorFunction.apply(commandType).execute(chessGame);
    }
}
