package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.repository.MySummonerRepo;

import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("local_private")
class SummonerServiceTest {

    @Autowired
    private SummonerService summonerService;
    @Autowired
    private OriannaService oriannaService;
    @Autowired
    private MySummonerRepo mySummonerRepo;

    private static SummonerResponseDto summonerResponseDto = null;
    private static final String NAME ="이쁜학생";

    @BeforeEach
    public void saveSummoner() {
        mySummonerRepo.deleteAll();
        summonerResponseDto = null;
        while (summonerResponseDto == null){
            summonerResponseDto = oriannaService.summonerFindByNameAndSave(NAME);
        }
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
        SummonerResponseDto summonerResponseDto
                = oriannaService.summonerFindByNameAndSave(SummonerServiceTest.summonerResponseDto.getSummonerName());

        //when
        String id = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getId();
        String name = Orianna.summonerNamed(NAME).withRegion(Region.KOREA).get().getName();

        //then
        Assertions.assertThat(summonerResponseDto.getSummonerId()).isEqualTo(id);
        Assertions.assertThat(summonerResponseDto.getSummonerName()).isEqualTo(name);
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