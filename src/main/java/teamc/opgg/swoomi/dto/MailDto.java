package teamc.opgg.swoomi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MailDto {
    private String[] to;
    private Date sentDate;
    private String subject;
    private String text;
}
