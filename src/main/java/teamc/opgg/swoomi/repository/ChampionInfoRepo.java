package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.entity.ChampionInfo;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ChampionInfoRepo extends JpaRepository<ChampionInfo, Long> {

    Optional<ChampionInfo> findBySummonerName(String summonerName);

    Optional<ChampionInfo> findBySummonerNameAndMatchTeamCode(String summonerName, String matchTeamCode);
}
