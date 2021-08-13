package teamc.opgg.swoomi.entity;

import io.swagger.annotations.Api;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summoner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String summonerId;

    @Column(nullable = false)
    String accountId;

    @Column(nullable = false)
    int profileIconId;

    @Column(nullable = false)
    String summonerName;

    @Column(nullable = false)
    long summonerLevel;
}
