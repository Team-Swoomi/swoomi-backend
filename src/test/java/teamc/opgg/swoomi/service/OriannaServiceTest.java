package teamc.opgg.swoomi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.repository.MySummonerRepo;

@SpringBootTest
@Transactional
@ActiveProfiles("local_private")
class OriannaServiceTest {

    @Autowired
    OriannaService oriannaService;
    @Autowired
    SummonerService summonerService;
    @Autowired
    MySummonerRepo mySummonerRepo;

    @BeforeEach
    public void saveSummoner() {
        mySummonerRepo.deleteAll();
        SummonerResponseDto summonerResponseDto = null;
        while (summonerResponseDto == null){
            summonerResponseDto = oriannaService.summonerFindByNameAndSave("이쁜학생");
        }
    }

    @Test
    public void summonerSaveTest() throws Exception
    {
        //given

        //when

        //then
        Assertions.assertThat(oriannaService.summonerFindByNameAndSave("이쁜학생")).isNotNull();
    }
}