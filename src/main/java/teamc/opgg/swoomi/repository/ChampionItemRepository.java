package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ChampionItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionItemRepository extends JpaRepository<ChampionItem, Long> {
    Optional<List<ChampionItem>> findAllByChampionNameAndAndPosition(String championName, String position);
}
