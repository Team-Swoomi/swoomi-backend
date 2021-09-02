package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ItemPurchase;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPurchaseRepository extends JpaRepository<ItemPurchase, Long> {

    Optional<List<ItemPurchase>> findAllByMatchTeamCodeAndSummonerName(String matchTeamCode, String summonerName);
}
