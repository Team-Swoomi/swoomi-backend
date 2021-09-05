package teamc.opgg.swoomi.dto.socket;

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
    private String type;
}
