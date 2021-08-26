package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.MsgRoom;

import java.util.Optional;

@Repository
public interface MsgRoomRepository extends JpaRepository<MsgRoom, Long> {

    Optional<MsgRoom> findByRoomId(String roomId);
}