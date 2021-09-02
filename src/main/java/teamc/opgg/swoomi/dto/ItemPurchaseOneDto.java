package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.ItemPurchase;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaseOneDto {
    String matchTeamCode;
    String summonerName;
    String championName;
    String itemName;

    public ItemPurchase toEntity() {
        return ItemPurchase.builder()
                .matchTeamCode(matchTeamCode)
                .summonerName(summonerName)
                .championName(championName)
                .itemName(itemName)
                .build();
    }
}
