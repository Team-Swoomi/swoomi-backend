package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.SummonerRequestDto;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.OriannaService;
import teamc.opgg.swoomi.service.ResponseService;

@Slf4j
@Api(tags = "1. SUMMONER_V4")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SummonerController {

    private final ResponseService responseService;
    private final OriannaService oriannaService;

    @ApiOperation(value = "소환사 검색", notes = "소환사 명을 통해 소환사 정보를 가져옵니다.")
    @GetMapping("/summoner")
    public SingleResult<SummonerResponseDto> findSummonerBySummonerName(
            @ApiParam(value = "소환사 명", required = true)
            @RequestParam String summonerName) {

        log.info("summoner Name : " + summonerName);
        SummonerResponseDto summonerResponseDto = oriannaService.SummonerFindByNameAndSave(summonerName);
        return responseService.getSingleResult(summonerResponseDto);
    }
}
