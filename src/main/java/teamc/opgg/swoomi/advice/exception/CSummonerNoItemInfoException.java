package teamc.opgg.swoomi.advice.exception;

public class CSummonerNoItemInfoException extends RuntimeException {
    public CSummonerNoItemInfoException() {
        super();
    }

    public CSummonerNoItemInfoException(String message) {
        super(message);
    }

    public CSummonerNoItemInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
