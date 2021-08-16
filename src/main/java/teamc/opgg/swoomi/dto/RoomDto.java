package teamc.opgg.swoomi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.*;
import teamc.opgg.swoomi.entity.Room;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private String hostSummonerName;

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean matchStatus;

    private String roomNumber;

    public Room convertToEntity() {
        return Room.builder()
                .roomNumber(this.roomNumber)
                .hostSummonerName(this.hostSummonerName)
                .matchStatus(this.matchStatus)
                .build();
    }
}
