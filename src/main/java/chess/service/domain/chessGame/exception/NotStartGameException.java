package chess.service.domain.chessGame.exception;

public class NotStartGameException extends IllegalStateException {
    public NotStartGameException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
