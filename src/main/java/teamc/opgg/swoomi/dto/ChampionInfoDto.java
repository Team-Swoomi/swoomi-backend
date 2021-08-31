package teamc.opgg.swoomi.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import teamc.opgg.swoomi.entity.ChampionInfo;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChampionInfoDto {
    private String summonerName;
    private Integer dSpellTime;
    private Integer fSpellTime;
    private Integer rSpellTime;
    private Integer spellAccel;
    private Integer skillAccel;

    public ChampionInfo toEntity() {
        return ChampionInfo.builder()
                .summonerName(summonerName)
                .dSpellTime(dSpellTime)
                .fSpellTime(fSpellTime)
                .rSpellTime(rSpellTime)
                .spellAccel(spellAccel)
                .skillAccel(skillAccel)
                .build();
    }
}
