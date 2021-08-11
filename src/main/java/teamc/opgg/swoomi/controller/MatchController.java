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
import teamc.opgg.swoomi.service.MatchService;

@Controller
@RequestMapping("/v1/match")
@Api(tags = {"Match 상태 및 인증 관련 API"})
public class MatchController {

    @Autowired
    private MatchService matchService;

    /**
     * GetMatchStatus
     * 소환사명을 파라미터로 받아 현재 게임 시작 여부를 리턴한다.
     * @param summonerName
     * @return dto
     */
    @GetMapping("/{summonerName}")
    @ResponseBody
    @ApiOperation(value = "게임 시작 여부 반환", response = MatchDto.class)
    public MatchDto getMatchStatus(@ApiParam(value = "소환사명", required = true) @PathVariable String summonerName) {
        MatchDto dto = new MatchDto();
        try {
            matchService.getMatchStatus(summonerName, dto);
        } catch (Exception e) {
            e.printStackTrace();
            dto.setStatus(500);
            dto.setMessage(e.getMessage());
        } finally {
            return dto;
        }
    };
}
