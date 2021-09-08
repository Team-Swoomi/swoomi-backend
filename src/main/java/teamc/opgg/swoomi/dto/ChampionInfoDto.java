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
    private String championName;
    private Double dSpellTime;
    private Double fSpellTime;
    private Double rSpellTime;
    private String dSpellName;
    private String fSpellName;
    private Integer spellAccel;
    private Integer skillAccel;

    public ChampionInfo toEntity() {
        return ChampionInfo.builder()
                .summonerName(summonerName)
                .championName(championName)
                .dSpellTime(dSpellTime)
                .fSpellTime(fSpellTime)
                .dSpellName(dSpellName)
                .fSpellName(fSpellName)
                .rSpellTime(rSpellTime)
                .spellAccel(spellAccel)
                .skillAccel(skillAccel)
                .build();
    }
}
