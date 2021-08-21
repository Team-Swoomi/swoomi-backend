package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.QrService;
import teamc.opgg.swoomi.service.ResponseService;
import teamc.opgg.swoomi.service.SummonerService;

@Service
@Api(tags = {"4. QR code"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class QrController {

    private final QrService qrService;
    private final SummonerService summonerService;
    private final ResponseService responseService;

    @ApiOperation(value = "QR 코드 제공", notes = "웹으로 접근 시 모바일로 접근할 수 있도록 qr코드 제공")
    @GetMapping("/qr/{name}")
    public SingleResult<String> getQrCodeBySummonerName(
            @ApiParam(value = "소환사 명", required = true)
            @PathVariable("name") String summonerName) {

        String name = summonerService
                .findBySummonerName(summonerName)
                .getSummonerName()
                .replaceAll(" ", "%20");
        String qrCodeURL = qrService.getQrCodeURL(name);
        return responseService.getSingleResult(qrCodeURL);
    }
}
