package teamc.opgg.swoomi.entity;

import lombok.Data;
import lombok.Getter;
import teamc.opgg.swoomi.dto.RoomDto;

import javax.persistence.*;

@Data
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomSeq;

    @Column(name = "host_summoner_name", nullable = false)
    private String hostSummonerName;

    @Column(name = "match_status", nullable = false)
    private boolean matchStatus;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    public RoomDto convertToDto() {
        RoomDto dto = new RoomDto();
        dto.setRoomNumber(this.getRoomNumber());
        dto.setHostSummonerName(this.getHostSummonerName());
        dto.setMatchStatus(this.isMatchStatus());
        return dto;
    }
}
