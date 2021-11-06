package teamc.opgg.swoomi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
