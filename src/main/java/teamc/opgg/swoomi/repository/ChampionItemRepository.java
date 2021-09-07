package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ChampionItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionItemRepository extends JpaRepository<ChampionItem, Long> {
    Optional<List<ChampionItem>> findAllByChampionNameAndPosition(String championName, String position);

    Optional<List<ChampionItem>> findAllByChampionName(String championName);

    Optional<ChampionItem> findFirstByItemNameAndChampionName(String itemName, String ChampionName);
}
