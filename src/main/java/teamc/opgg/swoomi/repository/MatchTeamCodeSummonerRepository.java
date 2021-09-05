package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.MatchTeamCodeSummoner;

import java.util.Optional;

@Repository
public interface MatchTeamCodeSummonerRepository extends JpaRepository<MatchTeamCodeSummoner, Long> {
    Optional<MatchTeamCodeSummoner> findFirstByMatchTeamCode(String matchTeamCode);
}
