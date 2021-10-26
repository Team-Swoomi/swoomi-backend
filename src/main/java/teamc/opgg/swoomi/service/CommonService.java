package teamc.opgg.swoomi.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import teamc.opgg.swoomi.dto.ChampionItemDto;
import teamc.opgg.swoomi.dto.ItemDto;
import teamc.opgg.swoomi.entity.ChampionItem;
import teamc.opgg.swoomi.repository.ChampionHasItemRepo;
import teamc.opgg.swoomi.repository.ChampionItemRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonService {
    private final ChampionItemRepository championItemRepository;

    @Transactional
    public void refreshFrequentItems(JsonArray jarr) {
        championItemRepository.deleteAll();
        Gson gson = new Gson();

        for (JsonElement e : jarr) {
            ChampionItemDto dto = gson.fromJson(e, ChampionItemDto.class);
            for (int i=0; i<dto.getItems().size(); i++) {
                ItemDto item = dto.getItems().get(i);
                if (item.getSkillAccel().isEmpty()) {
                    item.setSkillAccel("0");
                }
                ChampionItem championItem = ChampionItem.builder()
                        .championName(dto.getId())
                        .skillAccel(item.getSkillAccel())
                        .englishName(item.getEnglishName())
                        .itemName(item.getName())
                        .src(item.getSrc())
                        .position(dto.getPosition())
                        .build();

                initChampionNameHasItem();
                championItemRepository.save(championItem);
            }
        }
    }

    @Transactional
    public void initChampionNameHasItem() {
        ChampionHasItemRepo nameRepo = ChampionHasItemRepo.getInstance();

        if (nameRepo.getChampionSet().isEmpty()) {
            List<ChampionItem> all = championItemRepository.findAll();
            for (ChampionItem championItem : all) {
                nameRepo.getChampionSet().add(championItem.getChampionName());
            }
        }
    }

    public ResponseEntity<String> pingUpstreamServer() {
        String riotUrl = "https://developer.riotgames.com/api-status/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        response = restTemplate.getForEntity(riotUrl, String.class);
        String replacedBody = response.getBody().replaceAll("\\s+", "").toUpperCase();
        if (replacedBody.matches(".*(CHAMPION-V3)\\(.*(KR)*\\).*") ||
            replacedBody.matches(".*(SPECTATOR-V4)\\(.*(KR)*\\).*") ||
            replacedBody.matches(".*(SUMMONER-V4)\\(.*(KR)*\\).*")) {
            response = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response = new ResponseEntity<String>(HttpStatus.OK);
        }
        return response;
    }
}
