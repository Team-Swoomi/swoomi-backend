package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.MySummoner;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummonerRequestDto {
    private String summonerName;

    public MySummoner toEntity() {
        return MySummoner.builder()
                .summonerName(summonerName)
                .build();
    }
}

