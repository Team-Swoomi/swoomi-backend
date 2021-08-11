package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.dto.MatchDto;
import teamc.opgg.swoomi.util.ConstantStore;

@Service
public class MatchService {

    public MatchDto getMatchStatus(String summonerName, MatchDto dto) {
        Summoner summoner = Orianna.summonerNamed(summonerName).withRegion(Region.KOREA).get();
        boolean inGame = false;
        String message = "";


        if (summoner.exists()) {
            CurrentMatch match = Orianna.currentMatchForSummoner(summoner).get();
            inGame = match.exists();
            message = ConstantStore.RESPONSE_SUCCESS;
        } else {
            message = ConstantStore.NO_SUMMONER;
        }

        dto.setMatchStatus(inGame);
        dto.setMessage(message);
        return dto;
    }
}
