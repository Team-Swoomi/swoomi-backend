package teamc.opgg.swoomi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teamc.opgg.swoomi.entity.Summoner;

@Getter
@Setter
@NoArgsConstructor
public class SummonerRequestDto {
    private String summonerName;

    @Builder
    public SummonerRequestDto(String summonerName) {
        this.summonerName = summonerName;
    }
}

