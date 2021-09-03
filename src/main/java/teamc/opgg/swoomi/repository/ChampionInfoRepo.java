package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.entity.ChampionInfo;

import java.util.Optional;

public interface ChampionInfoRepo extends JpaRepository<ChampionInfo, Long> {

    Optional<ChampionInfo> findBySummonerName(String summonerName);
}
