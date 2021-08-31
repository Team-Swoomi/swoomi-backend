package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.repository.SummonerRepo;

@SpringBootTest
@Transactional
@ActiveProfiles("local_private")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SummonerServiceTest {

    @Autowired
    private SummonerService summonerService;
    @Autowired
    private OriannaService oriannaService;
    @Autowired
    private SummonerRepo summonerRepo;

    private static SummonerResponseDto summonerResponseDto;
    private static String NAME ="이쁜학생";

    @BeforeEach
    public void saveSummoner() {
        summonerResponseDto = oriannaService.SummonerFindByNameAndSave(NAME);
    }

    @Test
    public void findBySummonerId() throws Exception {
        //given
        String summonerId = summonerResponseDto.getSummonerId();

        //when
        String oriannaSummonerId = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getId();

        //then
        Assertions.assertThat(summonerId).isEqualTo(oriannaSummonerId);
    }

    @Test
    public void findBySummonerName() throws Exception {
        //given
        String summonerName = summonerResponseDto.getSummonerName();

        //when
        String name = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getName();

        //then
        Assertions.assertThat(summonerName).isEqualTo(name);
    }

    @Test
    public void checkRepoOrianna() throws Exception
    {
        //given
        MySummoner mySummoner = summonerRepo
                .findBySummonerId(summonerResponseDto.getSummonerId())
                .orElseThrow(CSummonerNotFoundException::new);

        //when
        String id = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getId();
        String accountId = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getAccountId();

        //then
        Assertions.assertThat(mySummoner.getSummonerId()).isEqualTo(id);
        Assertions.assertThat(mySummoner.getAccountId()).isEqualTo(accountId);
    }

    @Test
    public void checkServiceOrianna() throws Exception
    {
        //given
        SummonerResponseDto summonerService = this.summonerService.findBySummonerName(NAME);
        Summoner oriannaSummoner = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get();

        //when

        //then
        Assertions.assertThat(summonerService.getSummonerId()).isEqualTo(oriannaSummoner.getId());
        Assertions.assertThat(summonerService.getSummonerName()).isEqualTo(oriannaSummoner.getName());
    }
}