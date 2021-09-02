package teamc.opgg.swoomi.advice.exception;

public class CSummonerNoRuneInfoException extends RuntimeException {
    public CSummonerNoRuneInfoException() {
        super();
    }

    public CSummonerNoRuneInfoException(String message) {
        super(message);
    }

    public CSummonerNoRuneInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
