package teamc.opgg.swoomi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ChampionItem")
public class ChampionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String championName;
    private String position;
    private String itemName;
    private String skillAccel;
    private String englishName;
    private String src;
}
