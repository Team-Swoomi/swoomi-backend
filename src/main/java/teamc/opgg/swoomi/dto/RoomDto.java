package teamc.opgg.swoomi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import teamc.opgg.swoomi.entity.Room;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDto extends CommonDto {
    private String hostSummonerName;
    private boolean matchStatus;
    private String roomNumber;

    public Room convertToEntity() {
        Room room = new Room();
        room.setRoomNumber(this.getRoomNumber());
        room.setHostSummonerName(this.getHostSummonerName());
        room.setMatchStatus(this.isMatchStatus());
        return room;
    }
}
