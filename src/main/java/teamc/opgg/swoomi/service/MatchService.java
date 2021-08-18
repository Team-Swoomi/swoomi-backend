package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.datapipeline.kernel.data.SummonerAPI;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.MatchDto;
import teamc.opgg.swoomi.dto.PlayerDto;
import teamc.opgg.swoomi.util.ConstantStore;

import java.util.ArrayList;
import java.util.List;

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

    public List<PlayerDto> getOpData(String summonerName) {
        Summoner summoner = Orianna.summonerNamed(summonerName).withRegion(Region.KOREA).get();
        if (!summoner.exists()) {
            log.info("NO SUMMONER NAMED : " + summonerName);
            throw new CSummonerNotFoundException();
        }
        if (!summoner.isInGame()) {
            log.info("SUMMONER '" + summonerName + "' NOT IN GAME");
            throw new CSummonerNotInGameException();
        }
        List<PlayerDto> playerDtos = new ArrayList<>();
        SearchableList<Player> sList = summoner.getCurrentMatch().getParticipants();

        Player tempPlayer = sList.find((playerName) -> playerName.getSummoner().getName().equals(summonerName));
        String teamId = tempPlayer.getTeam().toString();

        SearchableList<Player> opList = sList.filter((player) -> !player.getTeam().toString().equals(teamId));
        
        for (Player p : opList) {
            PlayerDto dto = PlayerDto.builder().summonerName(p.getSummoner().getName())
                    .championName(p.getChampion().getName())
                    .championImgUrl(p.getChampion().getImage().getURL())
                    .ultImgUrl(p.getChampion().getSpells().get(3).getImage().getURL())
                    .spellDImgUrl(p.getSummonerSpellD().getImage().getURL())
                    .spellFImgUrl(p.getSummonerSpellF().getImage().getURL())
                    .build();

            playerDtos.add(dto);
        }
        return playerDtos;
    }
}
