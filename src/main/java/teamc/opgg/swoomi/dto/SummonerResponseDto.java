package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.MySummoner;

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

    public SummonerResponseDto(MySummoner summoner) {
        this.summonerId = summoner.getSummonerId();
        this.summonerName = summoner.getSummonerName();
    }
}
