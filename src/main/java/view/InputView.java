package view;

import java.util.Scanner;
import view.command.CommandType;
import view.command.SeparatedInput;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);
    public static final String MOVE_POSITION_REGEX_FORMAT = "^[a-h][1-8]$";

    public CommandType inputCommand() {
        return CommandType.from(SeparatedInput.from(scanner.nextLine()));
    }
}
