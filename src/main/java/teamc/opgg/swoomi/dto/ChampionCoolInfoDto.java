package teamc.opgg.swoomi.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChampionCoolInfoDto {
    private Double cooltimeD;
    private String spellDName;
    private Double cooltimeF;
    private String spellFName;
    private Double cooltimeR;
}
