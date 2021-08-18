package teamc.opgg.swoomi.controller;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import teamc.opgg.swoomi.dto.MatchDto;
import teamc.opgg.swoomi.dto.PlayerDto;
import teamc.opgg.swoomi.entity.response.ListResult;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.MatchService;
import teamc.opgg.swoomi.service.ResponseService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1/match")
@Api(tags = {"2.Match Status"})
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private ResponseService responseService;

    /**
     * GetMatchStatus
     * 소환사명을 파라미터로 받아 현재 게임 시작 여부를 리턴한다.
     * @param summonerName
     * @return dto
     */
    @GetMapping("/{summonerName}")
    @ResponseBody
    @ApiOperation(value = "게임 시작 여부 반환", notes = "소환사명을 받아 현재 게임 시작 여부를 리턴합니다.")
    public SingleResult<MatchDto> getMatchStatus(@ApiParam(value = "소환사명", required = true) @PathVariable String summonerName) {
        MatchDto dto = matchService.getMatchStatus(summonerName);
        return responseService.getSingleResult(dto);
    }

    @GetMapping("/data/{summonerName}")
    @ResponseBody
    @ApiOperation(value ="상대팀 데이터 반환", notes="소환사명을 받아 현재 게임 상대팀의 모든 정보를 가져옵니다.")
    public ListResult<PlayerDto> getOpData(@ApiParam(value = "소환사명", required = true) @PathVariable String summonerName) {
        List<PlayerDto> list = matchService.getOpData(summonerName);
        return responseService.getListResult(list);
    }
}
