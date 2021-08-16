package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.MatchDto;
import teamc.opgg.swoomi.util.ConstantStore;

@Service
@Slf4j
public class MatchService {
    public MatchDto getMatchStatus(String summonerName) {
        MatchDto dto = new MatchDto();
        Summoner summoner = Orianna.summonerNamed(summonerName).withRegion(Region.KOREA).get();

        if (summoner.exists()) {
            dto.setMatchStatus(Orianna.currentMatchForSummoner(summoner).get().exists());
        } else {
            log.info("NO SUMMONER NAMED : " + summonerName);
            throw new CSummonerNotFoundException();
        }
        return dto;
    }
}
