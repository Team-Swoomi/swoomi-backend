package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.dto.RoomDto;
import teamc.opgg.swoomi.entity.Room;
import teamc.opgg.swoomi.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Transactional
    public void createRoom(RoomDto body) {
        Room room = body.convertToEntity();
        boolean inGame = Orianna.summonerNamed(body.getHostSummonerName()).withRegion(Region.KOREA).get().getCurrentMatch().exists();
        room.setMatchStatus(inGame);

        roomRepository.save(room);
    }
}
