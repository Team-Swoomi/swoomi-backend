package teamc.opgg.swoomi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.*;
import teamc.opgg.swoomi.dto.socket.ItemMessage;
import teamc.opgg.swoomi.dto.socket.Message;
import teamc.opgg.swoomi.service.ItemPurchaseService;
import teamc.opgg.swoomi.service.MsgService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MsgController {

    private final ItemPurchaseService itemPurchaseService;
    private final MsgService msgService;

    /***
     * publish [pub/comm/message/{teamId}]
     */
    @MessageMapping("/comm/message/{teamId}")
    @SendTo("/sub/comm/room/{teamId}")
    public Message message(@DestinationVariable String teamId,
                           Message message) {
        return message;
    }

    /*
    publish [pub/comm/item/{teamId}]
     */
    @MessageMapping("/comm/item/{teamId}")
    @SendTo("/sub/comm/item/{teamId}")
    public ItemMessage message(@DestinationVariable String teamId,
                               ItemMessage itemMessage) {

        for (String itemName : itemMessage.getItemNames()) {
            ItemPurchaseOneDto itemDto = ItemPurchaseOneDto.builder()
                    .matchTeamCode(teamId)
                    .itemName(itemName)
                    .summonerName(itemMessage.getSummonerName())
                    .championName(itemMessage.getChampionName())
                    .build();

            if (itemMessage.getMethod().equals("DELETE") && itemMessage.getType().equals("ITEM")) {
                itemPurchaseService.deletePurchaseItem(itemDto);
            } else {
                itemPurchaseService.setPurchaseItem(itemDto);
            }
        }

        return itemMessage;
    }

    /**
     * 새로운 유저가 접속하면
     * 현재 초기 데이터에서 변화된 모든 데이터 필요하기 때문에 (동기화)
     * 접속했다고 알리기 위함
     * @param teamId
     * @param dto
     * @return
     */
    @MessageMapping("/comm/newUser/{teamId}")
    @SendTo("/sub/comm/newUser/{teamId}")
    public NewUserDto newUser(@DestinationVariable String teamId,
                           NewUserDto dto) {
        return dto;
    }

    /**
     * 궁 레벨 정보 통신
     * @param matchTeamCode
     * @param dto
     * @return
     */
    @MessageMapping("/comm/ult/{teamId}")
    @SendTo("/sub/comm/ult/{teamId}")
    public UltDto ultMessage(@DestinationVariable String matchTeamCode,
                             UltDto dto) {
        return dto;
    }

    /**
     * 바람용 정보 통신
     * @param matchTeamCode
     * @param dto
     * @return
     */
    @MessageMapping("/comm/dragon/{matchTeamCode}")
    @SendTo("/sub/comm/dragon/{matchTeamCode}")
    public CloudDragonDto cloudDragonCount(@DestinationVariable String matchTeamCode,
                                           CloudDragonDto dto) {
        dto.setMatchTeamCode(matchTeamCode);
        msgService.cloudDragonCount(dto);
        return dto;
    }
}