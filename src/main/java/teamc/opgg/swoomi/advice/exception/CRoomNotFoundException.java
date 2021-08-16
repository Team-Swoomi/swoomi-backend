package teamc.opgg.swoomi.advice.exception;

public class CRoomNotFoundException extends RuntimeException {
    public CRoomNotFoundException() {
        super();
    }

    public CRoomNotFoundException(String message) {
        super(message);
    }

    public CRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
