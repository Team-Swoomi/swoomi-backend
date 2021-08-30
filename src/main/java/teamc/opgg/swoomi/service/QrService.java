package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import teamc.opgg.swoomi.advice.exception.CQrCodeFailException;
import teamc.opgg.swoomi.dto.QrDto;

import java.awt.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrService {

    @Autowired
    Environment env;

    private final String realUrl = "http://3.34.111.116:8070";
    private final String localUrl = "http://localhost:8070";
    private final RestTemplate restTemplate;

    public QrDto getQrCodeURL(String summonerName) {

        byte[] qRCodeByteArr;
        String URL;
        String property = env.getProperty("spring.profiles.include");
        if (property != null && property.contains("real")) {
            URL = "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl="+realUrl+"/v1/summoner/";
        } else {
            URL = "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl="+localUrl+"/v1/summoner/";
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL + summonerName + "/");

        try {
            qRCodeByteArr = restTemplate.getForObject(builder.build().encode().toUri(), byte[].class);
            if (qRCodeByteArr == null) throw new CQrCodeFailException();
        } catch (RestClientException e) {
            throw new CQrCodeFailException();
        }

        return QrDto.builder()
                .qrCode(qRCodeByteArr)
                .qrUrl(builder.toUriString())
                .build();
    }
}
