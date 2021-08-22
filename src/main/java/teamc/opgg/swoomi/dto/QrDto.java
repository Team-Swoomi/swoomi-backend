package teamc.opgg.swoomi.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrDto {
    private String qrUrl;
    private Byte[] qrCode;
}
