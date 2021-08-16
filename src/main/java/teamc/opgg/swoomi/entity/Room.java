package teamc.opgg.swoomi.entity;

import lombok.*;
import teamc.opgg.swoomi.dto.RoomDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long roomSeq;

    @Column(name = "host_summoner_name", nullable = false)
    String hostSummonerName;

    @Column(name = "match_status", nullable = false)
    boolean matchStatus;

    @Column(name = "room_number", nullable = false, unique = true)
    String roomNumber;

    public RoomDto convertToDto() {
        return RoomDto.builder()
                .roomNumber(this.roomNumber)
                .matchStatus(this.matchStatus)
                .hostSummonerName(this.hostSummonerName)
                .build();
    }
}
