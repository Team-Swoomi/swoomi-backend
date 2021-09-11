package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.ChampionAccelInfoDto;
import teamc.opgg.swoomi.dto.ChampionCoolInfoDto;
import teamc.opgg.swoomi.dto.ChampionInfoDto;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.ChampionInfoService;
import teamc.opgg.swoomi.service.ResponseService;

@Slf4j
@RestController
@Api(tags = "5. 챔피언 쿨감/쿨타임 정보")
@RequiredArgsConstructor
@RequestMapping("/champion")
public class ChampionInfoController {

    private final ChampionInfoService championInfoService;
    private final ResponseService responseService;

    @GetMapping("/defaultCooltimeInfo")
    @ApiOperation(value = "챔피언 궁/스펠 쿨감 미적용 쿨타임 정보", notes = "소환사 명 입력 시 쿨감이 적용되지 않은 궁/스펠 쿨타임 반환")
    public SingleResult<ChampionCoolInfoDto> findChampionDefaultCooltimeInfo(
            @ApiParam(value = "소환사명", required = true)
            @RequestParam String summonerName,
            @ApiParam(value = "궁 레벨 (lv 1, 2, 3)", required = true)
            @RequestParam int ultLevel)
    {
        log.info("SUMMONER NAME : " + summonerName);
        log.info("ULT LEVEL : " + ultLevel);

        ChampionCoolInfoDto championCoolInfoDto = championInfoService.getInitialCooltimeInfo(summonerName, ultLevel);
        return responseService.getSingleResult(championCoolInfoDto);
    }

    @GetMapping("/calcedCooltimeInfo")
    @ApiOperation(value = "챔피언 궁/스펠 쿨감 적용 쿨타임 정보", notes = "소환사 명 입력 시 쿨감이 적용된 궁/스펠 쿨타임 반환")
    public SingleResult<ChampionCoolInfoDto> findChampionCalcedCooltimeInfo(
            @ApiParam(value = "소환사명", required = true)
            @RequestParam String summonerName,
            @ApiParam(value = "궁 레벨 (lv 1, 2, 3)", required = true)
            @RequestParam int ultLevel)
    {
        log.info("SUMMONER NAME : " + summonerName);
        log.info("ULT LEVEL : " + ultLevel);

        ChampionCoolInfoDto calcedCooltimeInfo = championInfoService
                .getCalcedCooltimeInfo(summonerName, ultLevel);
        return responseService.getSingleResult(calcedCooltimeInfo);
    }
}


