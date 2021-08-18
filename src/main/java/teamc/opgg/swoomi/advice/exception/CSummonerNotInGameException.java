package teamc.opgg.swoomi.advice.exception;

public class CSummonerNotInGameException extends RuntimeException {
    public CSummonerNotInGameException() {
        super();
    }

    public CSummonerNotInGameException(String message) {
        super(message);
    }

    public CSummonerNotInGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
