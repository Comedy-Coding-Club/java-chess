package chess.dto;

import chess.view.Command;

public record CommandDTO(Command command, String from, String to) {

    private static final int COMMAND_INDEX = 0;
    private static final int MOVE_FROM_INDEX = 1;
    private static final int MOVE_TO_INDEX = 2;

    public static CommandDTO from(String input) {
        String[] split = input.split(" ");
        Command command = Command.inputToCommand(split[COMMAND_INDEX]);
        if (command == Command.MOVE) {
            return new CommandDTO(command, split[MOVE_FROM_INDEX], split[MOVE_TO_INDEX]);
        }
        return new CommandDTO(command, null, null);
    }
}
