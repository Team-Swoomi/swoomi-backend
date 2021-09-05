package teamc.opgg.swoomi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
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
    private String summornerName;
}
