package chess.domain.chessGame.exception;

public class NotPlayingGameException extends IllegalStateException {

    public NotPlayingGameException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
