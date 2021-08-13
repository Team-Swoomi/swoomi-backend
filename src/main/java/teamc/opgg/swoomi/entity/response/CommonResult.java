package teamc.opgg.swoomi.entity.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

    @ApiModelProperty(value = "응답 성공 여부 : T / F")
    private boolean success;

    @ApiModelProperty(value = "응답 코드, 0 미만 에러, 0 초과 정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}
