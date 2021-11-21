package teamc.opgg.swoomi.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import teamc.opgg.swoomi.service.CommonService;

@Slf4j
@Component
@RequiredArgsConstructor
public class FrequentItemScheduledJob {

    private final CommonService commonService;

    @Scheduled(cron = "0 0 8 ? * TUE,SAT", zone = "Asia/Seoul")
    public void scheduleRefreshFrequentItem() {
        log.info("============== SCHEDULED JOB STARTED ==============");

        commonService.refreshFrequentItems();

        log.info("============== SCHEDULED JOB ENDED ==============");
    }
}
