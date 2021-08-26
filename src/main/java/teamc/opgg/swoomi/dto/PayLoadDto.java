package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayLoadDto {
    private String summonerName;
    private Integer dSpellTime;
    private Integer fSpellTime;
    private Integer ultTime;
}
