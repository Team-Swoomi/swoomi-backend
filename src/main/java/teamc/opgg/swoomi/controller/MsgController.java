package teamc.opgg.swoomi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.Message;
import teamc.opgg.swoomi.dto.PayLoadDto;
import teamc.opgg.swoomi.service.MatchService;

@RestController
@RequiredArgsConstructor
public class MsgController {

    private final MatchService matchService;
    private final SimpMessageSendingOperations sendingOperations;

    /***
     * publish [pub/comm/message]
     * @param message
     */
    @MessageMapping("/comm/message")
    public void message(Message message) {
        if (Message.MessageType.INITIAL.equals(message.getType())) {
            // TODO : 5명의 궁극기, 스펠 D/F 초기 시간 반환
            PayLoadDto[] initialCoolTime = matchService.getInitialCoolTime(message.getSender());
            message.setMessage(initialCoolTime);
        } else if (Message.MessageType.UPDATE_SPELL.equals(message.getType())) {
            // TODO : 특점 챔피언 스펠 시간 조정
        } else if (Message.MessageType.UPDATE_ULT.equals(message.getType())) {
            // TODO : 특정 챔피언 궁극기 시간 조정
        }
        /*
        PUBLISHER 에게 온 메시지를 모든 Subscriber 에게 BroadCasting
         */
        sendingOperations.convertAndSend("/sub/comm/room/" + message.getRoomId(), message);
    }
}