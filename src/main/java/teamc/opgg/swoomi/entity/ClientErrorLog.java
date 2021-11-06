package teamc.opgg.swoomi.entity;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import teamc.opgg.swoomi.dto.ClientErrorLogDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientErrorLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String errorMessage;
    private LocalDateTime createdAt;

    public ClientErrorLogDto toDto() {
        return ClientErrorLogDto.builder()
                .createdAt(createdAt)
                .errorMessage(errorMessage)
                .build();
    }
}
