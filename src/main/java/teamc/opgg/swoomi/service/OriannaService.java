package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Platform;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.SummonerSpell;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SPELL;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.redis.CacheKey;
import teamc.opgg.swoomi.repository.MySummonerRepo;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OriannaService {

    private final MySummonerRepo mySummonerRepo;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Cacheable(value = CacheKey.SUMMONER)
    public SummonerResponseDto summonerFindByNameAndSave(String summonerName) {
        Optional<MySummoner> firstSummoner = mySummonerRepo.findFirstBySummonerName(summonerName);
        if (firstSummoner.isEmpty()) {
            log.info("NOT IN DB NAME : [" + summonerName + "]");
            summonerName = refactoringName(summonerName);
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
                    Optional<MySummoner> bySummonerId = mySummonerRepo.findFirstBySummonerId(summoner.getId());
                    if (bySummonerId.isPresent()) {
                        bySummonerId.get().setSummonerName(summoner.getName());
                        return bySummonerId.get().toDto();
                    } else {
                        new Thread(() -> mySummonerRepo.save(mySummoner)).start();
                        return mySummoner.toDto();
                    }
                }
            } catch (IllegalStateException illegalStateException) {
                log.error("NO SUMMONER");
                throw new CSummonerNotFoundException();
            }
        }
        return firstSummoner.get().toDto();
    }

    private String refactoringName(String summonerName) {
        // only Eng => 전부 붙임
        // else => 영어, 숫자 제외 다 띄움
        boolean onlyEng = true;

        summonerName = summonerName.replaceAll(" ", "");

        for (int i = 0; i < summonerName.length(); i++) {
            char c = summonerName.charAt(i);
            if (!((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0'))) {
                onlyEng = false;
                break;
            }
        }

        if (!onlyEng) {
            StringBuilder spaceName = new StringBuilder();
            for (int i = 0; i < summonerName.length(); i++) {
                char c = summonerName.charAt(i);
                if ((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0') || c == ' ') {
                    spaceName.append(c);
                } else if (i == summonerName.length() - 1) {
                    spaceName.append(c);
                } else {
                    spaceName.append(c).append(" ");
                }
            }
            summonerName = spaceName.toString();
        }
        return summonerName;
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

    public SummonerResponseDto findSummonerByRiot(String summonerName) {
        Summoner summoner = Orianna
                .summonerNamed(summonerName)
                .withRegion(Region.KOREA)
                .get();
        if (!summoner.exists()) {
            throw new CSummonerNotFoundException();
        } else {
            MySummoner mySummoner = MySummoner.builder()
                    .accountId(summoner.getAccountId())
                    .summonerId(summoner.getId())
                    .summonerName(summoner.getName())
                    .summonerLevel(summoner.getLevel())
                    .profileIconId(summoner.getProfileIcon().getId())
                    .build();
            return mySummoner.toDto();
        }
    }
}
