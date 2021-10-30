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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import teamc.opgg.swoomi.dto.ChampionItemDto;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.CommonService;
import teamc.opgg.swoomi.service.ResponseService;


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
    public CommonResult refreshFrequentItems() {
        Object obj = restTemplate.getForObject("https://backend.swoomi.me:9000/champion/item", Object.class);
        Gson gson = new Gson();

        JsonObject jobj = (JsonObject) gson.toJsonTree(obj);
        JsonArray jarr = jobj.getAsJsonArray("champData");

        commonService.refreshFrequentItems(jarr);
        return responseService.getSuccessResult();
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingUpstreamServer() {
        return commonService.pingUpstreamServer();
    }
}
