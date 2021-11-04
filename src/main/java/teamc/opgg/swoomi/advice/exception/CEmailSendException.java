package teamc.opgg.swoomi.advice.exception;

public class CEmailSendException extends RuntimeException {
    public CEmailSendException() {
        super();
    }

    public CEmailSendException(String message) {
        super(message);
    }

    public CEmailSendException(String message, Throwable cause) {
        super(message, cause);
    }

    public CEmailSendException(Throwable cause) {
        super(cause);
    }
}
