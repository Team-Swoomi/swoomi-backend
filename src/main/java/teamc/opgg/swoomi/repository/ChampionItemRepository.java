package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ChampionItem;

@Repository
public interface ChampionItemRepository extends JpaRepository<ChampionItem, Long> {
}
