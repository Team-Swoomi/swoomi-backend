package teamc.opgg.swoomi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import teamc.opgg.swoomi.dto.ChampionItemDto;
import teamc.opgg.swoomi.dto.ClientErrorLogDto;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.entity.response.ListResult;
import teamc.opgg.swoomi.service.CommonService;
import teamc.opgg.swoomi.service.ResponseService;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


@Slf4j
@RestController
@RequestMapping("/v1/common")
@Api(tags = {"7. Common API"})
public class CommonController {

    @Autowired
    ResponseService responseService;

    @Autowired
    CommonService commonService;

    @Qualifier("restTemplate")
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/refresh")
    public CommonResult refreshFrequentItems() throws Exception {
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//
//                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
//                }
//        };
//
//        SSLContext sc = SSLContext.getInstance("SSL");
//        sc.init(null, trustAllCerts, new SecureRandom());
//        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        commonService.refreshFrequentItems();
        return responseService.getSuccessResult();
    }

    @GetMapping("/ping")
    public ResponseEntity<Void> pingUpstreamServer() {
        return commonService.pingUpstreamServer();
    }

    @GetMapping("/client/error")
    public ListResult<ClientErrorLogDto> logClientError() {
        return responseService.getListResult(commonService.getClientError());
    }

    @PostMapping("/client/error")
    public ResponseEntity<Void> logClientError(@RequestBody ClientErrorLogDto dto) {
        return commonService.logClientError(dto);
    }
}
