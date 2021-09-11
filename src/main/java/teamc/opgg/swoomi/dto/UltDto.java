package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UltDto {

    private String summonerName;
    private String type;
    private Integer ultLevel;

}
