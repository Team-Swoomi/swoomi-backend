package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private String name;
    private int skillAccel;
    private String englishName;
    private String src;
}
