package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.MySummoner;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummonerResponseDto {
    private String summonerId;
    private String summonerName;
}
