package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.*;
import teamc.opgg.swoomi.entity.response.ListResult;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/match")
@Api(tags = {"2. Match Status"})
public class MatchController {

    private final MatchService matchService;
    private final ResponseService responseService;
    private final OppositeInfoService oppositeInfoService;
    private final OriannaService oriannaService;

    /**
     * GetMatchStatus
     * 소환사명을 파라미터로 받아 현재 게임 시작 여부를 리턴한다.
     */
    @GetMapping("/status/{summonerName}")
    @ApiOperation(value = "게임 시작 여부 반환", notes = "소환사명을 받아 현재 게임 시작 여부를 리턴합니다.")
    public SingleResult<MatchDto> getMatchStatus(
            @ApiParam(value = "소환사명", required = true)
            @PathVariable String summonerName) {
        String summonerId = oriannaService.summonerFindByNameAndSave(summonerName).getSummonerId();
        MatchDto dto = matchService.getMatchStatus(summonerId);
        return responseService.getSingleResult(dto);
    }

    @GetMapping("/status/by-match-team-code/{matchTeamCode}")
    @ApiOperation(value = "게임 시작 여부 반환 by matchTeamCode", notes = "matchTeamCode를 받아 현재 게임 시작 여부를 리턴합니다.")
    public SingleResult<MatchDto> getMatchStatusByMatchTeamCode(
            @ApiParam(value = "matchTeamCode", required = true)
            @PathVariable String matchTeamCode) {
        MatchDto dto = matchService.getMatchStatusByMatchTeamCode(matchTeamCode);
        return responseService.getSingleResult(dto);
    }

    @GetMapping("/opponents/{summonerName}")
    @ApiOperation(value ="자신의 소환사명으로 상대팀 데이터 반환", notes="소환사명을 받아 현재 게임 상대팀의 모든 정보를 가져옵니다.")
    public ListResult<PlayerDto> getOpData(@ApiParam(value = "소환사명", required = true) @PathVariable String summonerName) {
        List<PlayerDto> list = oppositeInfoService.getOpData(summonerName);
        return responseService.getListResult(list);
    }

    @GetMapping("/opponents/by-match-team-code/{matchTeamCode}")
    @ApiOperation(value ="matchTeamCode로 상대팀 데이터 반환", notes="매치 팀 코드를 받아 현재 게임 상대팀의 모든 정보를 가져옵니다.")
    public ListResult<PlayerDto> getOpDataMatchTeamCode(@ApiParam(value = "매치 팀 코드", required = true) @PathVariable String matchTeamCode) {
        List<PlayerDto> list = oppositeInfoService.getOpDataMatchTeamCode(matchTeamCode);
        return responseService.getListResult(list);
    }

    @GetMapping("/get-match-team-code/{summonerName}")
    @ApiOperation(value = "matchTeamCode 생성 및 반환", notes = "매치 상태 T/F 와 매치 ID + 진영정보 B/R를 합해서 반환합니다.")
    public SingleResult<MatchStatusDto> getMatchTeamCode(
            @ApiParam(value = "소환사 명", required = true)
            @PathVariable String summonerName) {
        if (matchService.getMatchStatus(oriannaService.summonerFindByNameAndSave(summonerName).getSummonerId()).isMatchStatus()) {
            return responseService.getSingleResult(matchService.getMatchTeamCode(summonerName));
        } else throw new CSummonerNotInGameException();
    }

    @GetMapping("/frequent-items/{championName}/{position}")
    @ApiOperation(value = "챔피언별 자주가는 아이템", notes = "챔피언명과 포지션을 받아서 자주가는 아이템을 리턴 해 줍니다.")
    public ListResult<ItemDto> getFrequentItems(
            @ApiParam(value = "챔피언 명(e.g. 가렌 | 마오카이 | 럭스", required = true)
            @PathVariable String championName,
            @ApiParam(value = "챔피언 포지션(e.g. top | mid | support | jungle | adc)", required = true)
            @PathVariable String position) {
        List<ItemDto> list = matchService.getFrequentItems(championName, position);
        return responseService.getListResult(list);
    }
}
