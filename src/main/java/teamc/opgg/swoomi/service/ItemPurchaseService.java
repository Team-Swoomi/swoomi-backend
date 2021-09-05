package teamc.opgg.swoomi.service;

import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNoItemInfoException;
import teamc.opgg.swoomi.dto.ItemPurchaseOneDto;
import teamc.opgg.swoomi.dto.ItemPurchaserInfoDto;
import teamc.opgg.swoomi.entity.ChampionItem;
import teamc.opgg.swoomi.entity.ItemPurchase;
import teamc.opgg.swoomi.repository.ChampionInfoRepo;
import teamc.opgg.swoomi.repository.ChampionItemRepository;
import teamc.opgg.swoomi.repository.ItemPurchaseRepository;

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
    public void setItemPurchase(ItemPurchaseOneDto itemPurchaseOneDto) {
        if (championInfoRepo.findBySummonerName(itemPurchaseOneDto.getSummonerName()).isPresent()) {
            championInfoRepo.findBySummonerName(itemPurchaseOneDto.getSummonerName()).get().setUpdated(true);
        }
        itemPurchaseRepository.save(itemPurchaseOneDto.toEntity());
    }
}
