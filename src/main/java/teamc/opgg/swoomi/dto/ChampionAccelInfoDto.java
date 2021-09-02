package teamc.opgg.swoomi.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampionAccelInfoDto {
    private String summonerName;
    private Integer spellAccel;
    private Integer skillAccel;
}
