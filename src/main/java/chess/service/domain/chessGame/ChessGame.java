package chess.service.domain.chessGame;

import chess.service.domain.board.Board;
import chess.service.domain.chessGame.exception.NotEndGameException;
import chess.service.domain.chessGame.exception.NotPlayingGameException;
import chess.service.domain.chessGame.exception.NotStartGameException;
import chess.service.domain.location.Location;
import chess.service.domain.piece.Color;
import chess.service.domain.piece.Score;
import java.util.function.Supplier;

public abstract class ChessGame {

    private final int gameId;

    protected ChessGame(int gameId) {
        this.gameId = gameId;
    }

    public abstract boolean isEnd();

    /**
     * 체스 게임을 시작합니다.
     * @param checkRestart 재시작의 상황에서 재시작 여부를 입력받을 수 있는 Supplier
     * @return 시작된 체스 게임
     */
    public abstract ChessGame startGame(Supplier<Boolean> checkRestart);

    /**
     * 체스 게임을 종료합니다.
     * @return 종료된 체스 게임
     */
    public abstract ChessGame endGame();


    /**
     * 체스판의 기물을 이동시킵니다.
     * @param source 이동시킬 기물의 위치
     * @param target 기물의 목적지
     * @return 기물이 이동한 이후의 상태를 가진 체스게임
     * @throws NotPlayingGameException 게임이 진행중이지 않은 경우
     */
    public abstract ChessGame move(Location source, Location target) throws NotPlayingGameException;

    /**
     * 진행중인 게임의 보드를 반환합니다.
     * @return 진행중인 게임의 보드
     * @throws NotStartGameException 게임이 시작되지 않은 경우
     */
    public abstract Board getBoard() throws NotStartGameException;

    /**
     * color 턴의 기물 점수를 반환합니다.
     * @param color 턴 정보
     * @return color 턴의 점수
     * @throws NotStartGameException 게임이 시작되지 않은 경우
     */
    public abstract Score getScore(Color color) throws NotStartGameException;

    /**
     * 승리 플레이어를 반환합니다.
     * @return 승리 플레이어의 색
     * @throws NotEndGameException 아직 승패가 정해지지 않은 경우
     */
    public abstract Color getWinner() throws NotEndGameException;

    /**
     * 현재 턴 정보를 반환합니다.
     * @return 현재 턴 정보
     * @throws NotPlayingGameException 게임이 시작되지 않은 경우
     */
    public abstract Color getTurn() throws NotPlayingGameException;

    public int getGameId() {
        return gameId;
    }
}
