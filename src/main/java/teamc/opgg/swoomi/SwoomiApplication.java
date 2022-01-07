package teamc.opgg.swoomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwoomiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwoomiApplication.class, args);
    }
}
