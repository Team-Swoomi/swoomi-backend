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

    private String realUrl = "https://swoomi.me";
    private String localUrl = "http://localhost:8070";
    private final RestTemplate restTemplate;

    public QrDto getQrCodeURL(String summonerName) {

        byte[] qRCodeByteArr;
        String URL;
        String property = env.getProperty("spring.profiles.include");
        UriComponentsBuilder builder;

        if (property != null && property.contains("real")) {
            URL = "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=" + realUrl+ "/room/" + summonerName;
        } else {
            URL = "https://chart.apis.google.com/chart?cht=qr&chs=150x150&chl=" + localUrl+ "/room/" + summonerName;
        }
        builder = UriComponentsBuilder.fromHttpUrl(URL);
        log.info(builder.build().encode().toUri()+"");
        log.info(builder.toUriString());
        log.info(URL);

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
