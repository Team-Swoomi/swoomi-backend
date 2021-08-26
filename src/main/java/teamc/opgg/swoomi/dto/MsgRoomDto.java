package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.MsgRoom;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MsgRoomDto {
    private String roomId;

    public MsgRoom toEntity() {
        return MsgRoom.builder().roomId(roomId).build();
    }
}
