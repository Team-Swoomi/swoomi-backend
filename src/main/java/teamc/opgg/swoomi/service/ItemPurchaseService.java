package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNoItemInfoException;
import teamc.opgg.swoomi.dto.ItemPurchaseOneDto;
import teamc.opgg.swoomi.dto.ItemPurchaserInfoDto;
import teamc.opgg.swoomi.entity.ChampionInfo;
import teamc.opgg.swoomi.entity.ChampionItem;
import teamc.opgg.swoomi.entity.ItemPurchase;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemPurchaseService {

    private final ChampionInfoRepo championInfoRepo;
    private final ItemPurchaseRepository itemPurchaseRepository;
    private final ChampionItemRepository championItemRepository;
    private final ResponseService responseService;

    @Transactional(readOnly = true)
    public Integer getTotalItemSkillAccelFromSummoner(ItemPurchaserInfoDto purchaseReqDto) {
        List<ItemPurchase> itemPurchases = itemPurchaseRepository.findAllByMatchTeamCodeAndSummonerName(
                        purchaseReqDto.getMatchTeamCode(),
                        purchaseReqDto.getSummonerName())
                .orElseThrow(CSummonerNoItemInfoException::new);

        int totalItemSkillAccel = 0;
        Optional<ChampionItem> championItem;
        for (ItemPurchase item : itemPurchases)
        {
            championItem = championItemRepository
                    .findFirstByItemNameAndChampionName(
                            item.getItemName(),
                            purchaseReqDto.getChampionName());
            if (championItem.isPresent()) {
                totalItemSkillAccel += Integer.parseInt(championItem.get().getSkillAccel());
            } else totalItemSkillAccel += 0;
        }

        Optional<ChampionInfo> championInfo
                = championInfoRepo.findBySummonerName(purchaseReqDto.getSummonerName());

        if (championInfo.isPresent() && championInfo.get().getHasMystic()) {
            totalItemSkillAccel += championInfo.get().getCountLegendary() * 5;
        }

        log.info("SKILL ACCEL : " + totalItemSkillAccel);
        return totalItemSkillAccel;
    }

    @Transactional(readOnly = true)
    public Integer getTotalItemSpellAccelFromSummoner(ItemPurchaserInfoDto purchaseReqDto) {
        List<ItemPurchase> itemPurchases = itemPurchaseRepository
                .findAllByMatchTeamCodeAndSummonerName(
                        purchaseReqDto.getMatchTeamCode(),
                        purchaseReqDto.getSummonerName())
                .orElse(new ArrayList<>());

        int itemSpellAccel = 0;
        for (ItemPurchase item : itemPurchases) {
            if (item.getItemName().equals("명석함의 아이오니아 장화")) {
                itemSpellAccel += 12;
                break;
            }
        }

        log.info("SPELL ACCEL : " + itemSpellAccel);
        return itemSpellAccel;
    }

    @Transactional
    public CommonResult setPurchaseItem(ItemPurchaseOneDto itemDto) {
        String itemName = itemDto.getItemName();
        String summonerName = itemDto.getSummonerName();

        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            ChampionInfo info = championInfoRepo.findBySummonerName(summonerName).get();
            info.setUpdated(true);

            if (LegendaryItemRepo.getInstance().getLegendaryItemSet().contains(itemName)) {
                Integer countLegendary = info.getCountLegendary();
                info.setCountLegendary(++countLegendary);
            }

            if (MysticItemRepo.getInstance().getMysticItemSet().contains(itemName)) {
                info.setHasMystic(true);
            }

            itemPurchaseRepository.save(itemDto.toEntity());

            log.info("GET : " +  itemDto.getItemName() +" - " + itemDto.getSummonerName());
            return responseService.getSuccessResult();
        }
        throw new CSummonerNoItemInfoException();
    }

    @Transactional
    public CommonResult deletePurchaseItem(ItemPurchaseOneDto itemDto) {
        String matchTeamCode = itemDto.getMatchTeamCode();
        String summonerName = itemDto.getSummonerName();
        String itemName = itemDto.getItemName();

        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            ChampionInfo info = championInfoRepo.findBySummonerName(summonerName).get();
            info.setUpdated(true);

            itemPurchaseRepository.removeItemPurchaseByMatchTeamCodeAndSummonerNameAndItemName(
                    matchTeamCode,
                    summonerName,
                    itemName
            );

            if (LegendaryItemRepo.getInstance().getLegendaryItemSet().contains(itemName)) {
                Integer countLegendary = info.getCountLegendary();
                info.setCountLegendary(--countLegendary);
            }

            if (MysticItemRepo.getInstance().getMysticItemSet().contains(itemName)) {
                info.setHasMystic(false);
            }

            log.info("DELETE : " +  itemDto.getItemName() +" - " + itemDto.getSummonerName());
            return responseService.getSuccessResult();
        }
        throw new CSummonerNoItemInfoException();
    }
}
