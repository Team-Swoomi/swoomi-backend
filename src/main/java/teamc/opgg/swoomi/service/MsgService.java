package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CMsgRoomNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.CloudDragonDto;
import teamc.opgg.swoomi.dto.MatchStatusDto;
import teamc.opgg.swoomi.dto.MsgRoomDto;
import teamc.opgg.swoomi.entity.CloudDragonCount;
import teamc.opgg.swoomi.entity.MsgRoom;
import teamc.opgg.swoomi.repository.CloudDragonRepository;
import teamc.opgg.swoomi.repository.MsgRoomRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final MatchService matchService;
    private final CloudDragonRepository cloudDragonRepository;
    private final MsgRoomRepository msgRoomRepository;

    public List<MsgRoomDto> findAllRoom() {
        return msgRoomRepository
                .findAll()
                .stream()
                .map(MsgRoom::toDto)
                .collect(Collectors.toList());
    }

    public MsgRoomDto findById(String roomId) {
        MsgRoom room = msgRoomRepository.findByRoomId(roomId).orElseThrow(CMsgRoomNotFoundException::new);
        return room.toDto();
    }

    public String createRoom(String name) {
        MatchStatusDto matchTeamCode = matchService.getMatchTeamCode(name);
        if (matchTeamCode.getIsStarted()) {
            String thisRoomEndPoint = matchTeamCode.getMatchTeamCode();

            if (msgRoomRepository.findByRoomId(thisRoomEndPoint).isEmpty()) {
                MsgRoom room = MsgRoom.builder()
                        .roomId(thisRoomEndPoint)
                        .build();
                msgRoomRepository.save(room);
                return room.getRoomId();
            } else return msgRoomRepository.findByRoomId(thisRoomEndPoint).get().getRoomId();
        }
        throw new CSummonerNotInGameException();
    }

    @Transactional
    public void cloudDragonCount(CloudDragonDto dto) {
        Optional<CloudDragonCount> optionalCloudDragonCount =
                cloudDragonRepository.findCloudDragonCountByMatchTeamCode(dto.getMatchTeamCode());
        if (optionalCloudDragonCount.isPresent()) {
            CloudDragonCount cloudDragonCount = optionalCloudDragonCount.get();
            cloudDragonCount.setDragonCount(dto.getDragonCount());
        } else {
            CloudDragonCount cloudDragonCount = CloudDragonCount.builder()
                    .dragonCount(dto.getDragonCount())
                    .matchTeamCode(dto.getMatchTeamCode())
                    .build();

            cloudDragonRepository.save(cloudDragonCount);
        }
    }
}
