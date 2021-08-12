package teamc.opgg.swoomi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import teamc.opgg.swoomi.util.ConstantStore;

@Getter
@Setter
public class CommonDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(hidden = true)
    private int status;

    @ApiModelProperty(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public void sucess() {
        this.status = 200;
        this.message = ConstantStore.RESPONSE_SUCCESS;
    }

    public void failed(String message) {
        this.status = 500;
        this.message = message;
    }

}
