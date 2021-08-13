package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.Summoner;

@Getter
@Setter
@NoArgsConstructor
public class SummonerResponseDto {
    private String summonerId;
    private String summonerName;

    @Builder
    public SummonerResponseDto(String summonerId, String summonerName) {
        this.summonerId = summonerId;
        this.summonerName = summonerName;
    }

    public Summoner toEntity() {
        return Summoner.builder()
                .summonerId(summonerId)
                .summonerName(summonerName)
                .build();
    }
}
