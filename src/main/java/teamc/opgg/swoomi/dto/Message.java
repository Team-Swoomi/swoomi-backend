package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String summonerName;
    private Integer dSpellTime;
    private Integer fSpellTime;
    private Integer ultTime;
}
