package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CRoomNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.RoomDto;
import teamc.opgg.swoomi.entity.Room;
import teamc.opgg.swoomi.repository.RoomRepository;
import teamc.opgg.swoomi.util.ConstantStore;

import java.util.Optional;

@Service
@Slf4j
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public void createRoom(RoomDto body) {
        Summoner summoner = Orianna.summonerNamed(body.getHostSummonerName()).withRegion(Region.KOREA).get();
        if (summoner.exists()) {
            boolean inGame = summoner.getCurrentMatch().exists();
            body.setMatchStatus(inGame);
            Room room = body.convertToEntity();
            roomRepository.save(room);
        } else {
            log.info("NO SUMMONER NAMED : " + body.getHostSummonerName());
            throw new CSummonerNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public RoomDto findRoom(String roomNumber) {
        Room room = roomRepository.findOneByRoomNumber(roomNumber).orElseThrow(CRoomNotFoundException::new);
        RoomDto dto = room.convertToDto();
        return dto;
    }
}
