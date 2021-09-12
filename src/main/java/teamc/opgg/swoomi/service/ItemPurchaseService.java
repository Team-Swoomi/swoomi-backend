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
            String itemName = item.getItemName();
            championItem = championItemRepository
                    .findFirstByItemNameAndChampionName(
                            itemName,
                            purchaseReqDto.getChampionName());
            if (championItem.isPresent()) {
                totalItemSkillAccel += Integer.parseInt(championItem.get().getSkillAccel());
            } else totalItemSkillAccel += 0;
        }

        Optional<ChampionInfo> championInfo
                = championInfoRepo.findBySummonerName(purchaseReqDto.getSummonerName());

        if (championInfo.isPresent()) {
            if (championInfo.get().getHasMystic()) {
                totalItemSkillAccel += championInfo.get().getCountLegendary() * 5;
            }
        }

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
        return itemSpellAccel;
    }

    @Transactional
    public CommonResult setPurchaseItem(ItemPurchaseOneDto itemPurchaseOneDto) {
        String itemName = itemPurchaseOneDto.getItemName();
        String summonerName = itemPurchaseOneDto.getSummonerName();

        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            championInfoRepo.findBySummonerName(summonerName).get().setUpdated(true);

            if (LegendaryItemRepo.getInstance().getLegendaryItemSet().contains(itemName)) {
                Integer countLegendary = championInfoRepo.findBySummonerName(summonerName).get().getCountLegendary();
                championInfoRepo.findBySummonerName(summonerName).get().setCountLegendary(countLegendary + 1);
            }

            if (MysticItemRepo.getInstance().getMysticItemSet().contains(itemName)) {
                championInfoRepo.findBySummonerName(summonerName).get().setHasMystic(true);
            }

            itemPurchaseRepository.save(itemPurchaseOneDto.toEntity());
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
            championInfoRepo.findBySummonerName(summonerName).get().setUpdated(true);

            itemPurchaseRepository.removeItemPurchaseByMatchTeamCodeAndSummonerNameAndItemName(
                    matchTeamCode,
                    summonerName,
                    itemName
            );

            if (LegendaryItemRepo.getInstance().getLegendaryItemSet().contains(itemName)) {
                Integer countLegendary = championInfoRepo.findBySummonerName(summonerName).get().getCountLegendary();
                championInfoRepo.findBySummonerName(summonerName).get().setCountLegendary(countLegendary - 1);
            }

            if (MysticItemRepo.getInstance().getMysticItemSet().contains(itemName)) {
                championInfoRepo.findBySummonerName(summonerName).get().setHasMystic(false);
            }

            return responseService.getSuccessResult();
        }
        throw new CSummonerNoItemInfoException();
    }
}
