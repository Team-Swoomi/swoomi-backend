package teamc.opgg.swoomi.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {

    SUCCESS(1, "성공"),
    FAIL(-1, "실패");

    private int code;
    private String msg;
}
