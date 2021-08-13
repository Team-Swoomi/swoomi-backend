package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.entity.Summoner;

import java.util.Optional;

public interface SummonerRepo extends JpaRepository<Summoner, Long> {

    Optional<Summoner> findBySummonerName(String name);
}
