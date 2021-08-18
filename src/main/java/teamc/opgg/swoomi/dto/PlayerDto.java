package teamc.opgg.swoomi.dto;

import lombok.*;

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
    private String spellDImgUrl;
    private String spellFImgUrl;
}
