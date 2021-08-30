package teamc.opgg.swoomi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.dto.ChampionItemDto;
import teamc.opgg.swoomi.dto.ItemDto;
import teamc.opgg.swoomi.entity.ChampionItem;
import teamc.opgg.swoomi.repository.ChampionItemRepository;

@Slf4j
@Service
public class CommonService {

    @Autowired
    ChampionItemRepository championItemRepository;

    @Transactional
    public void refreshFrequentItems(ChampionItemDto dto) {
        championItemRepository.deleteAll();

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

            championItemRepository.save(championItem);
        }
    }
}
