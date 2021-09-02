package teamc.opgg.swoomi.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPurchaserInfoDto {
    String matchTeamCode;
    String summonerName;
    String championName;
}
