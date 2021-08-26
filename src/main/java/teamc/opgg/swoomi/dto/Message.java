package teamc.opgg.swoomi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    public enum MessageType {
        INITIAL, UPDATE_SPELL, UPDATE_ULT, BROADCAST
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String whoSummName;
    private PayLoadDto[] message;
}
