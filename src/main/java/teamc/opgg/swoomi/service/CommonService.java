package teamc.opgg.swoomi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CommonService {

    final static Set<String> championNameSet = new HashSet<>();

    @Autowired
    ChampionItemRepository championItemRepository;

    @Transactional
    public void refreshFrequentItems(ChampionItemDto dto) {
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

            championNameSet.add(dto.getId());
            initChampionNameHasItem();

            championItemRepository.save(championItem);
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

        if (response.getBody().replaceAll("\\s+","").contains("<td>KR</td><td>0%</td>")) {
            response = new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            response = new ResponseEntity(HttpStatus.OK);
        }
        return response;
    }
}
