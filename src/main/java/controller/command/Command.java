package controller.command;

import domain.ChessGame;
import java.util.Arrays;
import java.util.function.Function;
import view.OutputView;
import view.command.CommandDto;
import view.command.CommandType;

public enum Command {
    START(CommandType.START, StartCommandExecutor::new),
    END(CommandType.END, EndCommandExecutor::new),
    MOVE(CommandType.MOVE, MoveCommandExecutor::new),
    STATUS(CommandType.STATUS, StatusCommandExecutor::new),
    CONTINUE(CommandType.CONTINUE, ContinueCommandExecutor::new),
    ;

    private final CommandType commandType;
    private final Function<CommandDto, CommandExecutor> executorFunction;

    Command(
            final CommandType commandType,
            final Function<CommandDto, CommandExecutor> executorFunction
    ) {
        this.commandType = commandType;
        this.executorFunction = executorFunction;
    }

    public static Command from(final CommandDto commandDto) {
        return Arrays.stream(Command.values())
                .filter(command -> command.commandType == commandDto.getCommandType())
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void execute(final CommandDto commandDto, final OutputView outputView, final ChessGame chessGame) {
        executorFunction.apply(commandDto).execute(outputView, chessGame);
    }
}
