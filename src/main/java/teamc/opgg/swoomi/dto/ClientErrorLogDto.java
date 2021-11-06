package teamc.opgg.swoomi.dto;

import lombok.*;
import teamc.opgg.swoomi.entity.ClientErrorLog;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientErrorLogDto {
    private String errorMessage;
    private LocalDateTime createdAt; // Response 용

    public ClientErrorLog toEntity() {
        return ClientErrorLog.builder()
                .errorMessage(this.errorMessage)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
