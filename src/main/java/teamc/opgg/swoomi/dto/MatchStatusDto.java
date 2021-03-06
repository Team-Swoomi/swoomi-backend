package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchStatusDto {
    private Boolean isStarted;
    private String matchTeamCode;
}
