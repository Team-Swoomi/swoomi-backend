package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.ItemPurchase;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPurchaseDto {

    private String matchTeamCode;
    private String summonerName;
    private List<Long> itemIds;

    public List<ItemPurchase> convertToEntity() {
        List<ItemPurchase> list = new ArrayList<>();
        for (Long itemId : itemIds) {
            list.add(ItemPurchase.builder()
                    .matchTeamCode(this.matchTeamCode)
                    .summonerName(this.summonerName)
                    .itemId(itemId).build());
        }
        return list;
    }
}
