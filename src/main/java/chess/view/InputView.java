package chess.view;

import chess.dto.CommandDTO;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputView {

    private static final String COMMAND_REGEX = "move ([a-zA-Z][1-8])? ([a-zA-Z][1-8])?|start|end|status";
    private static final String CONTINUE_REGEX = "^[yn]*$";
    private static final Pattern COMMAND_PATTERN = Pattern.compile(COMMAND_REGEX);
    private static final Pattern CONTINUE_PATTERN = Pattern.compile(CONTINUE_REGEX);
    private static final Scanner SCANNER = new Scanner(System.in);

    public CommandDTO readCommand() {
        String input = SCANNER.nextLine().strip();
        validateBlank(input);
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않은 명령어입니다.");
        }
        return CommandDTO.from(input);
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 위치 값입니다.");
        }
    }

    public boolean readStartNewGame() {
        System.out.println("진행 중인 게임이 있습니다. 이어서 진행하시겠습니까? (y / n)");
        String input = SCANNER.nextLine().strip();
        validateBlank(input);
        validateContinue(input);
        return input.equals("n");
    }

    private void validateContinue(String input) {
        Matcher matcher = CONTINUE_PATTERN.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않는 대답입니다. (y / n) 로 입력해주세요.");
        }
    }
}
