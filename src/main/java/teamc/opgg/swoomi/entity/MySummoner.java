package teamc.opgg.swoomi.entity;

import io.swagger.annotations.Api;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import teamc.opgg.swoomi.dto.SummonerResponseDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "my_summoner")
public class MySummoner extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String summonerId;

    @Column(nullable = false, unique = true)
    String accountId;

    @Column(nullable = false)
    int profileIconId;

    @Column(nullable = false, unique = true)
    String summonerName;

    @Column(nullable = false)
    long summonerLevel;

    @Override
    public String toString() {
        return "MySummoner{" +
                "id=" + id +
                ", summonerId='" + summonerId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", profileIconId=" + profileIconId +
                ", summonerName='" + summonerName + '\'' +
                ", summonerLevel=" + summonerLevel +
                '}';
    }

    public SummonerResponseDto toDto() {
        return SummonerResponseDto.builder()
                .summonerId(summonerId)
                .summonerName(summonerName)
                .build();
    }
}
