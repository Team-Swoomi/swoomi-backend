package teamc.opgg.swoomi.advice.exception;

public class CSummonerNotFoundException extends RuntimeException {
    public CSummonerNotFoundException() {
        super();
    }

    public CSummonerNotFoundException(String message) {
        super(message);
    }

    public CSummonerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
