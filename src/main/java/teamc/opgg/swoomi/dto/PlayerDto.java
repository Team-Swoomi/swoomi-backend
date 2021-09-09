package teamc.opgg.swoomi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {
    private String summonerName;
    private String championName;
    private String championImgUrl;
    private String ultImgUrl;
    private String spellDName;
    private String spellFName;
    private String spellDImgUrl;
    private String spellFImgUrl;
    private List<ItemDto> frequentItems;
}
