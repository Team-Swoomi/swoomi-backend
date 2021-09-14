package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.ItemPurchaseOneDto;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.ChampionInfoService;
import teamc.opgg.swoomi.service.ItemPurchaseService;

@Api(tags = {"6. 아이템"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/item")
public class ItemController {

    private final ItemPurchaseService itemPurchaseService;
    private final ChampionInfoService championInfoService;

    @PostMapping("/buy")
    @ApiOperation(value = "아이템 구매", notes = "소한사의 아이템 구매 목록에 추가합니다.")
    public CommonResult buyItem(
            @ApiParam(name = "아이템 구매 요청", required = true)
            @RequestBody ItemPurchaseOneDto itemPurchaseOneDto) {
        championInfoService.calculateAndSaveChampionInfo(itemPurchaseOneDto.getSummonerName(), 1);
        return itemPurchaseService.setPurchaseItem(itemPurchaseOneDto);
    }

    @PostMapping("/sell")
    @ApiOperation(value = "아이템 판매", notes = "소환사의 아이템 구매 목록에서 제거합니다.")
    public CommonResult sellItem(
            @ApiParam(name = "아이템 판매 요청", required = true)
            @RequestBody ItemPurchaseOneDto itemPurchaseOneDto) {
        return itemPurchaseService.deletePurchaseItem(itemPurchaseOneDto);
    }
}
