package teamc.opgg.swoomi.dto.socket;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemMessage {
    private String summonerName;
    private String championName;
    private List<String> itemNames;
    private String type;
    private String method;
}
