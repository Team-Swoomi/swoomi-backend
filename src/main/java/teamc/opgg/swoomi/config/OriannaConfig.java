package teamc.opgg.swoomi.config;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.datapipeline.common.expiration.ExpirationPeriod;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Configuration
public class OriannaConfig {

    @Value("${riot.api.key}")
    private String RIOT_API_KEY;

    @PostConstruct
    public void setApiKey() {
        Orianna.setRiotAPIKey(RIOT_API_KEY);
        Orianna.setDefaultPlatform(Platform.KOREA);
        Orianna.setDefaultRegion(Region.KOREA);

        Orianna.Configuration config = new Orianna.Configuration();
        config.setCurrentVersionExpiration(ExpirationPeriod.create(0, TimeUnit.SECONDS));
    }
}
