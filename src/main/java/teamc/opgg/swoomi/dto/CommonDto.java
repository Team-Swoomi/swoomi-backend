package teamc.opgg.swoomi.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import springfox.documentation.annotations.ApiIgnore;

@Getter
@Setter
public class CommonDto {

    private int status;
    private String message;
}
