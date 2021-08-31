package teamc.opgg.swoomi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "champion_info")
public class ChampionInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String summonerName;

    @Column(nullable = false)
    private Integer dSpellTime;

    @Column(nullable = false)
    private Integer fSpellTime;

    @Column(nullable = false)
    private Integer rSpellTime;

    @Column(nullable = false)
    private Integer spellAccel;

    @Column(nullable = false)
    private Integer skillAccel;
}
