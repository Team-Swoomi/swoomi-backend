package teamc.opgg.swoomi.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChampionItemDto {
    private String id;
    private String position;
    private List<ItemDto> items;
}
