package teamc.opgg.swoomi.config;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SwoomiRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) { log(message); }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) { log(message); }

    protected void log(String message) {
        logger.debug(
                URLDecoder.decode(message, StandardCharsets.UTF_8)
        );
    }
}
