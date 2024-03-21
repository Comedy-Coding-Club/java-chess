package chess.view;

import chess.domain.StartCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readStartCommand() {
        System.out.println("> 체스 게임을 시작합니다." + System.lineSeparator()
                + "> 게임 시작 : start" + System.lineSeparator()
                + "> 게임 종료 : end" + System.lineSeparator()
                + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
        return scanner.nextLine();
    }


    public List<String> readMoveCommand() {
        final String input = scanner.nextLine();
        validateBlank(input);
        if (StartCommand.END.getMessage().equals(input)) {
            return new ArrayList<>();
        }
        return Arrays.asList(input.split(" ")).subList(1, 3);
    }

    private void validateBlank(final String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 입력입니다.");
        }
    }
}
