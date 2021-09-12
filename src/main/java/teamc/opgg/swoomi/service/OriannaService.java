package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Image;
import com.merakianalytics.orianna.types.core.staticdata.Sprite;
import com.merakianalytics.orianna.types.core.staticdata.SummonerSpell;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SPELL;
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
                if (!summoner.exists()) {
                    throw new CSummonerNotFoundException();
                } else {
                    mySummoner = MySummoner.builder()
                            .accountId(summoner.getAccountId())
                            .summonerId(summoner.getId())
                            .summonerName(summoner.getName())
                            .summonerLevel(summoner.getLevel())
                            .profileIconId(summoner.getProfileIcon().getId())
                            .build();
                    summonerRepo.save(mySummoner);
                }
            } catch (IllegalStateException illegalStateException) {
                log.error("NO SUMMONER");
                throw new CSummonerNotFoundException();
            }
            return new SummonerResponseDto(mySummoner);
        }
    }

    public String findSpellImageByName(SPELL spell) {
        SummonerSpell summonerSpell = SummonerSpell
                .named(spell.getName())
                .withRegion(Region.KOREA)
                .withPlatform(Platform.KOREA)
                .get();
        return summonerSpell.getImage().getURL();
    }

    public String findSummonerProfileImg(String summonerName) {
        Summoner summoner = Orianna.summonerNamed(summonerName).withRegion(Region.KOREA).get();
        if (summoner.exists()) {
            return summoner.getProfileIcon().getImage().getURL();
        } else {
            throw new CSummonerNotFoundException();
        }
    }
}
