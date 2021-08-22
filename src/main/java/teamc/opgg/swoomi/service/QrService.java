package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import teamc.opgg.swoomi.advice.exception.CQrCodeFailException;
import teamc.opgg.swoomi.dto.QrDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrService {

    private static final String LOCAL_URL =
            "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=http://localhost:8070/v1/summoner/";
    private static final String URL =
            "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=https://swoomi.com/v1/summoner/";
    private final RestTemplate restTemplate;

    public QrDto getQrCodeURL(String summonerName) {
        Byte[] qRCodeImg;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOCAL_URL + summonerName + "/");

        try {
            qRCodeImg = restTemplate.getForObject(builder.build().encode().toUri(), Byte[].class);
            if (qRCodeImg == null) throw new CQrCodeFailException();
        } catch (RestClientException e) {
            throw new CQrCodeFailException();
        }

        return QrDto.builder()
                .qrCode(qRCodeImg)
                .qrUrl(builder.toUriString())
                .build();
    }
}
