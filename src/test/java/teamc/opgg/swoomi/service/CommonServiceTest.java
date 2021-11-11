package teamc.opgg.swoomi.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.assertj.core.api.Assertions;
import org.hibernate.sql.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import teamc.opgg.swoomi.repository.ChampionHasItemRepo;
import teamc.opgg.swoomi.repository.ChampionItemRepository;

@SpringBootTest
@Transactional
@ActiveProfiles(profiles = "local_private")
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
//        Assertions.assertThat(instance.getChampionSet().contains("헤카림")).isEqualTo(true);
//        Assertions.assertThat(instance.getChampionSet().contains("가렌")).isEqualTo(true);
//        Assertions.assertThat(instance.getChampionSet().contains("문도박사")).isEqualTo(true);
//        Assertions.assertThat(instance.getChampionSet().contains("리신")).isEqualTo(true);
    }
}