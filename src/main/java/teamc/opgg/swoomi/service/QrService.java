package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import teamc.opgg.swoomi.advice.exception.CQrCodeFailException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrService {

    private static final String LOCAL_URL =
            "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=http://localhost:8070/v1/summoner/";
    private static final String URL =
            "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=https://swoomi.com/v1/summoner/";
    private final RestTemplate restTemplate;

    public String getQrCodeURL(String summonerName) {
        byte[] QRCodeImg;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LOCAL_URL + summonerName + "/");

        try {
            QRCodeImg = restTemplate.getForObject(builder.build().encode().toUri(), byte[].class);
            if (QRCodeImg == null) throw new CQrCodeFailException();
            Files.write(Paths.get("/Users/woonsik/Git/web-team-c-backend/src/main/resources/QrCodeImage/qr.png"), QRCodeImg);
        } catch (RestClientException | IOException e) {
            throw new CQrCodeFailException();
        }
        return builder.toUriString();
    }
}
