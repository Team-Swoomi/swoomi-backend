package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.entity.ClientErrorLog;

public interface ClientErrorLogRepository extends JpaRepository<ClientErrorLog, Long> {

}
