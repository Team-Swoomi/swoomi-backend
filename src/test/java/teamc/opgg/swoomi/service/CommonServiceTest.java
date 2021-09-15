package teamc.opgg.swoomi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.repository.ChampionHasItemRepo;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "real_private")
public class CommonServiceTest {

    @Autowired
    CommonService commonService;

    @Test
    public void 쿨감아이템_보유_챔피언_목록()
    {
        //given
        commonService.initChampionNameHasItem();

        //when
        ChampionHasItemRepo instance = ChampionHasItemRepo.getInstance();

        //then
        Assertions.assertThat(instance.getChampionSet().contains("헤카림")).isEqualTo(true);
        Assertions.assertThat(instance.getChampionSet().contains("가렌")).isEqualTo(true);
        Assertions.assertThat(instance.getChampionSet().contains("문도박사")).isEqualTo(true);
        Assertions.assertThat(instance.getChampionSet().contains("리신")).isEqualTo(true);
    }
}