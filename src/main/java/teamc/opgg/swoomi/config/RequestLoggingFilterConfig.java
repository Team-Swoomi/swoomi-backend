package teamc.opgg.swoomi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    public SwoomiRequestLoggingFilter loggingFilter() {
        SwoomiRequestLoggingFilter commonsRequestLoggingFilter
                = new SwoomiRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setMaxPayloadLength(10000);
        commonsRequestLoggingFilter.setIncludeHeaders(true);
        commonsRequestLoggingFilter.setBeforeMessagePrefix("# BEFORE REQUEST : ");
        commonsRequestLoggingFilter.setAfterMessagePrefix("# AFTER REQUEST : ");
        return commonsRequestLoggingFilter;
    }

}
