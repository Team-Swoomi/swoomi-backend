package teamc.opgg.swoomi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import teamc.opgg.swoomi.entity.ClientErrorLog;

@Getter
@Setter
@Builder
public class ClientErrorLogDto {
    private String errorMessage;

    public ClientErrorLog toEntity() {
        return ClientErrorLog.builder()
                .errorMessage(this.errorMessage)
                .build();
    }
}
