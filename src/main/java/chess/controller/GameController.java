package chess.controller;

import chess.domain.chessGame.ChessGame;
import chess.domain.chessGame.InitialGame;
import chess.domain.location.Location;
import chess.repository.GameDao;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;
import java.util.Optional;

public class GameController {
    private static final InputView INPUT_VIEW = new InputView();
    private static final OutputView OUTPUT_VIEW = new OutputView();
    private static final GameDao GAME_DAO = new GameDao();

    @FunctionalInterface
    private interface GameStateChanger {
        ChessGame change(ChessGame game);
    }

    private final Map<Command, GameStateChanger> commandFunctions;

    public GameController() {
        commandFunctions = Map.of(
                Command.START, this::startGame,
                Command.MOVE, this::move,
                Command.END, this::end,
                Command.STATUS, this::status
        );
    }

    public void run() {
        ChessGame game = createGame();
        try {
            playGame(game);
        } catch (RuntimeException exception) {
            OUTPUT_VIEW.printExceptionMessage("예기치 못한 동작입니다. " + exception.getMessage());
        }
    }

    private ChessGame createGame() {
        Optional<ChessGame> chessGame = GAME_DAO.loadGame();
        if (chessGame.isPresent() && INPUT_VIEW.checkLoadGame()) {
            ChessGame loadedGame = chessGame.get();
            OUTPUT_VIEW.printBoard(loadedGame.getBoard());
            return loadedGame;
        }
        OUTPUT_VIEW.printGameStart();
        return new InitialGame();
    }

    private void playGame(ChessGame game) {
        while (!game.isEnd()) {
            game = playTurn(game);
        }
    }

    private ChessGame playTurn(ChessGame chessGame) {
        try {
            Command command = INPUT_VIEW.readCommand();
            return executeCommand(chessGame, command);
        } catch (IllegalArgumentException | IllegalStateException exception) {
            OUTPUT_VIEW.printExceptionMessage(exception.getMessage());
            return playTurn(chessGame);
        }
    }

    private ChessGame executeCommand(ChessGame chessGame, Command command) {
        return Optional.ofNullable(commandFunctions.get(command))
                .orElseThrow(() -> new IllegalArgumentException("잘못된 커멘드 입력입니다."))
                .change(chessGame);
    }

    private ChessGame startGame(ChessGame chessGame) {
        chessGame = chessGame.startGame(INPUT_VIEW::checkRestartGame);
        OUTPUT_VIEW.printBoard(chessGame.getBoard());
        return chessGame;
    }

    private ChessGame move(ChessGame chessGame) {
        chessGame = movePiece(chessGame);
        OUTPUT_VIEW.printBoard(chessGame.getBoard());
        if (chessGame.isEnd()) {
            OUTPUT_VIEW.printWinner(chessGame);
            GAME_DAO.initialDB();
        }
        return chessGame;
    }

    private ChessGame movePiece(ChessGame chessGame) {
        String sourceInput = INPUT_VIEW.readLocation();
        String targetInput = INPUT_VIEW.readLocation();

        Location source = Location.of(sourceInput);
        Location target = Location.of(targetInput);

        return chessGame.move(source, target);
    }

    private ChessGame status(ChessGame chessGame) {
        OUTPUT_VIEW.printResult(chessGame);
        return chessGame;
    }

    private ChessGame end(ChessGame chessGame) {
        GAME_DAO.saveGame(chessGame);
        return chessGame.endGame();
    }
}
