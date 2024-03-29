package chess.view;

import chess.controller.Command;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public boolean checkLoadGame() {
        System.out.println("저장된 게임이 존재합니다. 게임을 로드하시겠습니까? (y/n)");
        String input = SCANNER.next();
        if ("y".equalsIgnoreCase(input)) {
            return true;
        }
        if ("n".equalsIgnoreCase(input)) {
            return false;
        }
        System.out.printf("잘못된 입력입니다. y 또는 n을 입력해 주세요 (현재 입력 : %s)%n", input);
        return checkLoadGame();
    }

    public Command readCommand() {
        String input = SCANNER.next();
        return Command.of(input);
    }

    public String readLocation() {
        return SCANNER.next();
    }

    public boolean checkRestartGame() {
        System.out.println("정말 게임을 재시작 하겠습니까? (y/n)");
        String input = SCANNER.next();
        if ("y".equalsIgnoreCase(input)) {
            return true;
        }
        if ("n".equalsIgnoreCase(input)) {
            return false;
        }
        System.out.printf("잘못된 입력입니다. y 또는 n을 입력해 주세요 (현재 입력 : %s)%n", input);
        return checkRestartGame();
    }
}
