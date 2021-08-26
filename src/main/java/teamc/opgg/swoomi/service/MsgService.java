package teamc.opgg.swoomi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import teamc.opgg.swoomi.advice.exception.CMsgRoomNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.MatchDto;
import teamc.opgg.swoomi.dto.MatchStatusDto;
import teamc.opgg.swoomi.dto.MsgRoomDto;
import teamc.opgg.swoomi.entity.MsgRoom;
import teamc.opgg.swoomi.repository.MsgRoomRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final MatchService matchService;
    private final ObjectMapper objectMapper;
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
        // TODO : roomId == Summoner MatchId + blue/red Team code
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
}
