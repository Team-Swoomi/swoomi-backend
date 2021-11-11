package teamc.opgg.swoomi.redis;

public class CacheKey {
    private CacheKey() {
    }

    public static final int DEFAULT_EXPIRE_SEC = 60;
    public static final String SUMMONER = "summoner";
    public static final int SUMMONER_EXPIRE_SEC = 180;
}
