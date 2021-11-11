package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.SPELL;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.redis.CacheKey;
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
    @GetMapping("/summoner/{name}")
    public SingleResult<SummonerResponseDto> findSummonerBySummonerName(
            @ApiParam(value = "소환사 명", required = true)
            @PathVariable("name") String summonerName) {

        SummonerResponseDto responseDto = oriannaService.summonerFindByNameAndSave(summonerName);
        log.info("RET NAME : " + responseDto.getSummonerName());
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "Riot 소환사 검색", notes = "공식서버 소환사 검색")
    @GetMapping("/summoner/riot/{name}")
    public SingleResult<SummonerResponseDto> findSummoner(
            @ApiParam(value = "소환사 명", required = true)
            @PathVariable("name") String summonerName
    ) {
        SummonerResponseDto responseDto = oriannaService.findSummonerByRiot(summonerName);
        log.info("RET NAME : " + responseDto.getSummonerName());
        return responseService.getSingleResult(responseDto);
    }

    @ApiOperation(value = "스펠 이미지 검색", notes = "스펠 명을 통해 스펠 이미지 URL을 가져옵니다.")
    @GetMapping("/spellURL")
    public SingleResult<String> findSpellImgUrl(
            @ApiParam(value = "스펠 명", required = true)
            @RequestParam SPELL spellName) {

        log.info("Spell Name : " + spellName.name());
        String spellImgURL = oriannaService.findSpellImageByName(spellName);
        return responseService.getSingleResult(spellImgURL);
    }

    @ApiOperation(value = "소환사 프로필 이미지 검색", notes = "소환사명을 통해 프로필 이미지 URL을 가져옵니다.")
    @GetMapping("/profileImgURL")
    public SingleResult<String> findSummonerProfileImg(
            @ApiParam(value = "소환사 명", required = true)
            @RequestParam String summonerName) {
        log.info("Summoner Name : " + summonerName);
        String summonerProfileImg = oriannaService.findSummonerProfileImg(summonerName);
        return responseService.getSingleResult(summonerProfileImg);
    }
}
