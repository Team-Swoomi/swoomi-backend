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
    @SendTo("/sub/comm/room/{teamId}")
    public ItemMessage message(@DestinationVariable String teamId,
                               ItemMessage itemMessage) {

        ItemPurchaseOneDto itemDto = ItemPurchaseOneDto.builder()
                .matchTeamCode(teamId)
                .itemName(itemMessage.getItemName())
                .summonerName(itemMessage.getSummonerName())
                .championName(itemMessage.getChampionName())
                .build();
        if (itemMessage.getType().equals("DELETE")) {
            itemPurchaseService.deletePurchaseItem(itemDto);
        } else {
            itemPurchaseService.setPurchaseItem(itemDto);
        }
        return itemMessage;
    }

    /**
     * 새로운 유저가 접속하면
     * 현재 감소하고 있는 쿨 타임 데이터 받기 위하여
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

    @MessageMapping("/comm/dragon/{matchTeamCode}")
    @SendTo("/sub/comm/dragon/{matchTeamCode}")
    public CloudDragonDto cloudDragonCount(@DestinationVariable String matchTeamCode,
                                           CloudDragonDto dto) {
        dto.setMatchTeamCode(matchTeamCode);
        msgService.cloudDragonCount(dto);
        return dto;
    }
}