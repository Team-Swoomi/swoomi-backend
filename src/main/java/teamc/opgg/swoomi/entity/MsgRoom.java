package teamc.opgg.swoomi.entity;

import lombok.*;
import teamc.opgg.swoomi.dto.MsgRoomDto;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "msg_room")
public class MsgRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    public MsgRoomDto toDto() {
        return MsgRoomDto.builder()
                .roomId(roomId)
                .build();
    }
}
