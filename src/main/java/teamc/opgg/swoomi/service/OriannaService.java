package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SummonerRequestDto;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.repository.SummonerRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class OriannaService {

    private final SummonerRepo summonerRepo;
    private final SummonerService summonerService;

    public SummonerResponseDto SummonerFindByNameAndSave(String summonerName) {
        try {
            return summonerService.findBySummonerName(summonerName);
        } catch (CSummonerNotFoundException notFoundException) {
            MySummoner mySummoner;
            try {
                Summoner summoner = Orianna
                        .summonerNamed(summonerName)
                        .withRegion(Region.KOREA)
                        .get();
                mySummoner = MySummoner.builder()
                        .accountId(summoner.getAccountId())
                        .summonerId(summoner.getId())
                        .summonerName(summoner.getName())
                        .summonerLevel(summoner.getLevel())
                        .profileIconId(summoner.getProfileIcon().getId())
                        .build();
                summonerRepo.save(mySummoner);
            } catch (IllegalStateException illegalStateException) {
                log.error("NO SUMMONER");
                throw new CSummonerNotFoundException();
            }
            return new SummonerResponseDto(mySummoner);
        }
    }
}
