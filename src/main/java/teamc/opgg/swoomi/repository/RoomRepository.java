package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamc.opgg.swoomi.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
