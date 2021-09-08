package teamc.opgg.swoomi.entity;

import lombok.*;
import teamc.opgg.swoomi.dto.ChampionAccelInfoDto;
import teamc.opgg.swoomi.dto.ChampionCoolInfoDto;
import teamc.opgg.swoomi.dto.ChampionInfoDto;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "champion_info")
public class ChampionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true)
    private String summonerName;
    private String championName;
    private Double dSpellTime;
    private Double fSpellTime;
    private Double rSpellTime;
    private String dSpellName;
    private String fSpellName;
    private Integer spellAccel;
    private Integer skillAccel;
    private Boolean hasMystic;
    private Integer countLegendary;
    private Boolean updated;

    public ChampionInfoDto toInfoDto() {
        return ChampionInfoDto.builder()
                .summonerName(summonerName)
                .dSpellTime(dSpellTime)
                .fSpellTime(fSpellTime)
                .rSpellTime(rSpellTime)
                .dSpellName(dSpellName)
                .fSpellName(fSpellName)
                .spellAccel(spellAccel)
                .skillAccel(skillAccel)
                .build();
    }

    public ChampionAccelInfoDto toAccelInfoDto() {
        return ChampionAccelInfoDto.builder()
                .summonerName(summonerName)
                .spellAccel(spellAccel)
                .skillAccel(skillAccel)
                .build();
    }

    public ChampionCoolInfoDto toCoolInfoDto() {
        return ChampionCoolInfoDto
                .builder()
                .cooltimeD(dSpellTime)
                .cooltimeF(fSpellTime)
                .cooltimeR(rSpellTime)
                .build();
    }
}