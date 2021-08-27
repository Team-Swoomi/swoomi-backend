package teamc.opgg.swoomi.controller;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Item;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.ItemDto;
import teamc.opgg.swoomi.dto.ItemMessage;
import teamc.opgg.swoomi.dto.ItemPurchaseDto;
import teamc.opgg.swoomi.dto.Message;
import teamc.opgg.swoomi.service.MatchService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MsgController {

    private final MatchService matchService;

    /***
     * publish [pub/comm/message/{teamId}]
     */
    @MessageMapping("/comm/message/{teamId}")
    @SendTo("/sub/comm/room/{teamId}")
    public Message message(@DestinationVariable String teamId,
                           Message message) {
        log.info("SUB : " + teamId);
        log.info(message.getWhoSummName());
        log.info(message.getDSpellTime() + "");
        log.info(message.getFSpellTime() + "");
        log.info(message.getUltTime() + "");
        return message;
    }

    /*
    publish [pub/comm/item/{teamId}]
     */
    @MessageMapping("/comm/item/{teamId}")
    @SendTo("/sub/comm/room/{teamId}")
    public ItemMessage message(@DestinationVariable String teamId,
                               ItemMessage itemMessage) {

        List<Long> items = new ArrayList<>();
        // itemMessage.getItemName();
        items.add(1L);

        ItemPurchaseDto itemPurchaseDto = ItemPurchaseDto.builder()
                .summonerName(itemMessage.getSummonerName())
                .matchTeamCode(teamId)
                .itemIds(items)
                .build();
        matchService.postItemPurchase(itemPurchaseDto);
        return itemMessage;
    }
}