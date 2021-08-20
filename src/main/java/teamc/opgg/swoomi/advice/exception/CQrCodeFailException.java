package teamc.opgg.swoomi.advice.exception;

public class CQrCodeFailException extends RuntimeException{
    public CQrCodeFailException() {
        super();
    }

    public CQrCodeFailException(String message) {
        super(message);
    }

    public CQrCodeFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
