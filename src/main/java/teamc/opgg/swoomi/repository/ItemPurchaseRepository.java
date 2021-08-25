package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ItemPurchase;

@Repository
public interface ItemPurchaseRepository extends JpaRepository<ItemPurchase, Long> {

}
