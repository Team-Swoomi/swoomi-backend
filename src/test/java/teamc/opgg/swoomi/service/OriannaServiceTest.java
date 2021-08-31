package teamc.opgg.swoomi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.repository.SummonerRepo;
import javax.swing.undo.CannotUndoException;

@SpringBootTest
@Transactional
@ActiveProfiles("real_private")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OriannaServiceTest {

    @Autowired
    OriannaService oriannaService;
    @Autowired
    SummonerService summonerService;
    @Autowired
    SummonerRepo summonerRepo;

    @Test
    public void summonerSaveTest() throws Exception
    {
        //given
        String summonerName = "이쁜학생";

        //when
        oriannaService.SummonerFindByNameAndSave(summonerName);

        //then
        MySummoner mySummoner = summonerRepo.findBySummonerName("이쁜학생").orElseThrow(CannotUndoException::new);
        SummonerResponseDto summoner = summonerService.findBySummonerName("이쁜학생");

        Assertions.assertThat(mySummoner.getSummonerId()).isEqualTo(summoner.getSummonerId());
    }
}