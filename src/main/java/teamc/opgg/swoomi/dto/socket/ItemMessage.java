package teamc.opgg.swoomi.dto.socket;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMessage {
    private String summonerName;
    private String championName;
    private String itemName;
    private String type;
}
