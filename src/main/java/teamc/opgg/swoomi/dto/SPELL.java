package teamc.opgg.swoomi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum SPELL {

    BARRIER("방어막"),
    CLEANSE("정화"),
    IGNITE("점화"),
    EXHAUST("탈진"),
    GHOST("유체화"),
    TELEPORT("순간이동"),
    SMITE("강타"),
    HEAL("회복");


    private String name;
}
