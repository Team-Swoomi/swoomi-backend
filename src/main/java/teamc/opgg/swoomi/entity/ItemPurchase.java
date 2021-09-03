package teamc.opgg.swoomi.entity;

import lombok.*;
import teamc.opgg.swoomi.dto.ItemDto;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "ItemPurchase")
public class ItemPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matchTeamCode;
    private String summonerName;
    private String championName;
    private String itemName;
}
