package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.ItemPurchase;
import teamc.opgg.swoomi.entity.Room;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPurchaseDto {

    private String matchTeamCode;
    private String summonerName;
    private ItemDto item;


    public ItemPurchase convertToEntity() {
        return ItemPurchase.builder()
                .matchTeamCode(this.matchTeamCode)
                .summonerName(this.summonerName)
                .englishName(this.item.getEnglishName())
                .name(this.item.getName())
                .skillAccel(this.item.getSkillAccel())
                .src(this.item.getSrc())
                .build();
    }
}
