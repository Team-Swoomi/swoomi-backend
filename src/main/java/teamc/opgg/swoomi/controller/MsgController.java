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
import teamc.opgg.swoomi.service.MatchService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MsgController {

    private final ItemPurchaseService itemPurchaseService;

    /***
     * publish [pub/comm/message/{teamId}]
     */
    @MessageMapping("/comm/message/{teamId}")
    @SendTo("/sub/comm/room/{teamId}")
    public Message message(@DestinationVariable String teamId,
                           Message message) {
        log.info("TEAM ID : " + teamId);
        log.info("소환사 명 : "+ message.getSummonerName());
        log.info("D : "+ message.getDSpellTime());
        log.info("F : "+ message.getFSpellTime());
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
            itemPurchaseService.setItemPurchase(itemDto);
        }
        return itemMessage;
    }
}