package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamc.opgg.swoomi.dto.SummonerRequestDto;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.ResponseService;

@Api(tags = "1. SUMMONER_V4")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class SummonerController {

    private final ResponseService responseService;

    @ApiOperation(value = "소환사 검색", notes = "소환사 명을 통해 소환사 정보를 가져옵니다.")
    @GetMapping("/summoner")
    public SingleResult<SummonerResponseDto> findSummonerBySummonerName(
            @ApiParam(value = "소환사 명", required = true)
            @RequestBody SummonerRequestDto summonerRequestDto) {
        SummonerResponseDto summonerResponseDto = new SummonerResponseDto();
        return responseService.getSingleResult(summonerResponseDto);
    }
}
