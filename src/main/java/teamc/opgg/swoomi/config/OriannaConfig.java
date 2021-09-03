package teamc.opgg.swoomi.config;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
public class OriannaConfig {

    @Value("${riot.api.key}")
    private String RIOT_API_KEY;

    @PostConstruct
    public void setApiKey() {
        Orianna.setRiotAPIKey(RIOT_API_KEY);
        Orianna.setDefaultPlatform(Platform.KOREA);
        Orianna.setDefaultRegion(Region.KOREA);
    }
}
