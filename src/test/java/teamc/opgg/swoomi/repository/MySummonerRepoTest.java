package teamc.opgg.swoomi.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.service.SummonerService;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySummonerRepoTest {

    @Autowired
    MySummonerRepo mySummonerRepo;

    @Autowired
    SummonerService summonerService;

    @BeforeEach
    public void save() {
        mySummonerRepo.deleteAll();
        mySummonerRepo.save(MySummoner.builder()
                .accountId("abcdef")
                .profileIconId(111)
                .summonerName("woonsik")
                .summonerLevel(100)
                .summonerId("abcedf1010990")
                .build());
    }

    @Test
    public void 소환사저장_레디스() throws Exception {
        //given

        //when
        MySummoner mySummoner = mySummonerRepo.findBySummonerName("woonsik").orElseThrow(CSummonerNotFoundException::new);

        //then
        assertNotNull(mySummoner);
        assertThat(mySummonerRepo.count()).isEqualTo(1);
        assertThat(mySummoner.getSummonerName()).isEqualTo("woonsik");
    }

    @Test
    public void 소환사수정_redis() throws Exception {
        //given

        //when
        MySummoner mySummoner = mySummonerRepo.findBySummonerName("woonsik").orElseThrow(CSummonerNotFoundException::new);
        mySummoner.setSummonerName("woonsik2");
        mySummonerRepo.save(mySummoner);

        //then
        assertThat(mySummonerRepo.count()).isEqualTo(1);
        MySummoner afterSummoner
                = mySummonerRepo.findBySummonerName("woonsik2").orElseThrow(CSummonerNotFoundException::new);
        assertThat(afterSummoner).isNotNull();
        assertThat(afterSummoner.getSummonerName()).isEqualTo("woonsik2");
    }

    @Test
    public void 소환사삭제_redis() throws Exception {
        //given

        //when
        MySummoner mySummoner = mySummonerRepo.findBySummonerName("woonsik").orElseThrow(CSummonerNotFoundException::new);
        mySummonerRepo.delete(mySummoner);

        //then
        assertThat(mySummonerRepo.count()).isEqualTo(0);
    }
}