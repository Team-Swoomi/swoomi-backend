package teamc.opgg.swoomi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "MatchTeamCodeSummoner")
public class MatchTeamCodeSummoner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchTeamCode;
    @Column(unique = true)
    private String summonerName;
}
