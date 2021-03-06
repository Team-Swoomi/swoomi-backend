package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.MySummoner;

import java.util.List;
import java.util.Optional;

@Repository
public interface MySummonerRepo extends JpaRepository<MySummoner, Long> {

    Optional<MySummoner> findBySummonerName(String name);

    Optional<MySummoner> findBySummonerId(String summonerId);

    Optional<MySummoner> findFirstBySummonerId(String summonerId);

    Optional<MySummoner> findByAccountId(String accountId);

    Optional<MySummoner> findFirstBySummonerName(String name);
}
