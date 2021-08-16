package teamc.opgg.swoomi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.Room;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value="SELECT room_seq, host_summoner_name, match_status, room_number FROM room WHERE room_number = :roomNumber", nativeQuery = true)
    Optional<Room> findOneByRoomNumber(@Param("roomNumber") String roomNumber);
}
