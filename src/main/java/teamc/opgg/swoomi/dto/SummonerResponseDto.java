package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.MySummoner;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummonerResponseDto implements Serializable {
    private String summonerId;
    private String summonerName;
}
