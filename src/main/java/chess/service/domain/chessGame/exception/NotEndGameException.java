package chess.service.domain.chessGame.exception;

public class NotEndGameException extends IllegalStateException{
    public NotEndGameException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
