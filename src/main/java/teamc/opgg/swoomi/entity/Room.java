package teamc.opgg.swoomi.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomSeq;
    private String hostSummonerName;
    private boolean matchStatus;
    private String roomNumber;
}
