package teamc.opgg.swoomi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.CloudDragonCount;

import java.util.Optional;

@Repository
public interface CloudDragonRepository extends JpaRepository<CloudDragonCount, Long> {
    Optional<CloudDragonCount> findCloudDragonCountByMatchTeamCode(String matchTeamCode);
}
