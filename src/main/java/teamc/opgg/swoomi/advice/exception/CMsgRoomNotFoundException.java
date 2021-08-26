package teamc.opgg.swoomi.advice.exception;

public class CMsgRoomNotFoundException extends RuntimeException {
    public CMsgRoomNotFoundException() {
        super();
    }

    public CMsgRoomNotFoundException(String message) {
        super(message);
    }

    public CMsgRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
