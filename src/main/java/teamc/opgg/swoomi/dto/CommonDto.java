package teamc.opgg.swoomi.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.annotations.ApiIgnore;
import teamc.opgg.swoomi.util.ConstantStore;

@Getter
@Setter
public class CommonDto {
    private int status;
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
