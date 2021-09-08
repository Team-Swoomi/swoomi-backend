package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudDragonDto {
    private String matchTeamCode;
    private Integer dragonCount;
}
