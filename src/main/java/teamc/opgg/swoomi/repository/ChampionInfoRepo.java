package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.dto.ChampionInfoDto;
import teamc.opgg.swoomi.entity.ChampionInfo;

import java.util.Optional;

public interface ChampionInfoRepo extends JpaRepository<ChampionInfo, Long> {

    Optional<ChampionInfoDto> findBySummonerName(String summonerName);
}
