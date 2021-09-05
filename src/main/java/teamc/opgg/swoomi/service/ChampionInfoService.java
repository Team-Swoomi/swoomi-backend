package teamc.opgg.swoomi.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.spectator.Runes;
import com.merakianalytics.orianna.types.core.staticdata.ChampionSpell;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNoRuneInfoException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.*;
import teamc.opgg.swoomi.entity.ChampionInfo;
import teamc.opgg.swoomi.repository.ChampionInfoRepo;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChampionInfoService {

    private final Gson gson;
    private final MatchService matchService;
    private final ChampionInfoRepo championInfoRepo;
    private final ItemPurchaseService itemPurchaseService;

    private Player getPlayer(String summonerName) {
        String matchTeamCode = matchService
                .getMatchTeamCode(summonerName)
                .getMatchTeamCode();
        int teamCode = Integer.parseInt(matchTeamCode.substring(matchTeamCode.length() - 3));
        Summoner summoner = Orianna.summonerNamed(summonerName).get();

        CurrentMatch currentMatch;
        if (summoner.isInGame()) {
            currentMatch = summoner.getCurrentMatch();
        } else throw new CSummonerNotInGameException();

        Player player;
        if (teamCode == 100) {
            player = currentMatch
                    .getBlueTeam()
                    .getParticipants()
                    .find(o -> o.getSummoner().getName().equals(summonerName));
        } else {
            player = currentMatch
                    .getRedTeam()
                    .getParticipants()
                    .find(o -> o.getSummoner().getName().equals(summonerName));
        }
        log.info("PLAYER : "+ player.getSummoner().getName());
        return player;
    }

    private boolean infoIsUpdated(String summonerName) {
        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            return championInfoRepo.findBySummonerName(summonerName).get().getUpdated();
        }
        return true;
    }

    @Transactional
    public ChampionAccelInfoDto getInitialRuneInfo(String summonerName) {
        Player player = getPlayer(summonerName);

        JsonObject runeJson = gson.fromJson(player.getRunes().toJSON(), JsonObject.class);
        JsonArray RunesData = runeJson.getAsJsonArray("data");

        int skillAccel = 0;
        int spellAccel = 0;

        for (JsonElement rune : RunesData) {
            if (rune.getAsInt() == 8347) {
                spellAccel += 18;
            } else if (rune.getAsInt() == 5007) {
                skillAccel += 8;
            }
        }
        return ChampionAccelInfoDto.builder()
                .summonerName(summonerName)
                .skillAccel(skillAccel)
                .spellAccel(spellAccel)
                .build();
    }

    public Integer getTotalItemSkillAccel(ItemPurchaserInfoDto purchaseReqDto) {
        return itemPurchaseService.getTotalItemSkillAccelFromSummoner(purchaseReqDto);
    }

    public Integer getTotalItemSpellAccel(ItemPurchaserInfoDto purchaseReqDto) {
        return itemPurchaseService.getTotalItemSpellAccelFromSummoner(purchaseReqDto);
    }

    @Transactional(readOnly = true)
    public ChampionCoolInfoDto getInitialCooltimeInfo(String summonerName, int ultLevel) {

        if (ultLevel < 0 || ultLevel >= 3) ultLevel = 1;

        Player player = getPlayer(summonerName);
        Double cooldownDSpell = player.getSummonerSpellD().getCooldowns().get(0);
        Double cooldownFSpell = player.getSummonerSpellF().getCooldowns().get(0);
        Double cooldownRSpell = player.getChampion().getSpells().get(3).getCooldowns().get(ultLevel - 1);

        log.info("cooldown D : " + cooldownDSpell);
        log.info("cooldown F : " + cooldownFSpell);
        log.info("cooldown R : " + cooldownRSpell);

        return ChampionCoolInfoDto.builder()
                .cooltimeD(cooldownDSpell)
                .cooltimeF(cooldownFSpell)
                .cooltimeR(cooldownRSpell)
                .build();
    }

    @Transactional
    public ChampionInfoDto calculateAndSaveChampionInfo(String summonerName, int ultLevel) {

        Player player = getPlayer(summonerName);
        MatchStatusDto matchTeamCode = matchService.getMatchTeamCode(summonerName);
        String championName = player.getChampion().getName();

        ItemPurchaserInfoDto purchaserInfoDto = ItemPurchaserInfoDto.builder()
                .championName(championName)
                .summonerName(summonerName)
                .matchTeamCode(matchTeamCode.getMatchTeamCode())
                .build();

        ChampionAccelInfoDto initialRuneInfo = getInitialRuneInfo(summonerName);
        int finalSpellAccel = initialRuneInfo.getSpellAccel() + getTotalItemSpellAccel(purchaserInfoDto);
        int finalSkillAccel = initialRuneInfo.getSkillAccel() + getTotalItemSkillAccel(purchaserInfoDto);

        ChampionCoolInfoDto championCoolInfoDto = getInitialCooltimeInfo(summonerName, ultLevel);
        Double cooltimeD = championCoolInfoDto.getCooltimeD();
        Double cooltimeF = championCoolInfoDto.getCooltimeF();
        Double cooltimeR = championCoolInfoDto.getCooltimeR();

        double spellCooldownPercent = (100 - (double) 100 * ((double) finalSpellAccel / (double) (100 + finalSpellAccel))) / 100;
        double skillCooldownPercent = (100 - (double) 100 * ((double) finalSkillAccel / (double) (100 + finalSkillAccel))) / 100;

        log.info("spell cooldown percent : " + spellCooldownPercent);
        log.info("skill cooldown percent : " + skillCooldownPercent);

        double cooltimeCalcedD = Math.round ((cooltimeD * spellCooldownPercent) * 100) / (double) 100;
        double cooltimeCalcedF = Math.round ((cooltimeF * spellCooldownPercent) * 100) / (double) 100;
        double cooltimeCalcedR = Math.round ((cooltimeR * skillCooldownPercent) * 100) / (double) 100;
        log.info("d : "+cooltimeCalcedD);
        log.info("f : "+cooltimeCalcedF);
        log.info("r : "+cooltimeCalcedR);

        ChampionInfo championInfo = ChampionInfo.builder()
                .summonerName(summonerName)
                .championName(championName)
                .dSpellTime(cooltimeCalcedD)
                .fSpellTime(cooltimeCalcedF)
                .rSpellTime(cooltimeCalcedR)
                .skillAccel(finalSkillAccel)
                .spellAccel(finalSpellAccel)
                .build();

        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            ChampionInfo info = championInfoRepo.findBySummonerName(summonerName).get();
            info.setDSpellTime(cooltimeCalcedD);
            info.setFSpellTime(cooltimeCalcedF);
            info.setRSpellTime(cooltimeCalcedR);
            info.setSkillAccel(finalSkillAccel);
            info.setSpellAccel(finalSpellAccel);
            info.setUpdated(false);
            return info.toInfoDto();
        }
        return championInfoRepo.save(championInfo).toInfoDto();
    }

    @Transactional(readOnly = true)
    public Integer getRuneSkillAccel(String summonerName) {
        ChampionInfoDto championInfoDto = championInfoRepo
                .findBySummonerName(summonerName)
                .orElseThrow(CSummonerNoRuneInfoException::new)
                .toInfoDto();
        return championInfoDto.getSkillAccel();
    }

    @Transactional(readOnly = true)
    public Integer getRuneSpellAccel(String summonerName) {
        ChampionInfoDto championInfoDto = championInfoRepo
                .findBySummonerName(summonerName)
                .orElseThrow(CSummonerNoRuneInfoException::new)
                .toInfoDto();
        return championInfoDto.getSpellAccel();
    }

    @Transactional(readOnly = true)
    public ChampionAccelInfoDto getTotalInfoAboutAccel(String summonerName) {
        String championName = getPlayer(summonerName).getChampion().getName();
        String matchTeamCode = matchService.getMatchTeamCode(summonerName).getMatchTeamCode();

        ItemPurchaserInfoDto purchaserInfoDto = ItemPurchaserInfoDto.builder()
                .championName(championName)
                .summonerName(summonerName)
                .matchTeamCode(matchTeamCode)
                .build();

        ChampionInfoDto championInfoDto = championInfoRepo
                .findBySummonerName(summonerName)
                .orElseThrow(CSummonerNoRuneInfoException::new)
                .toInfoDto();

        Integer totalSkillAccel = getTotalItemSkillAccel(purchaserInfoDto) + championInfoDto.getSkillAccel();
        Integer totalSpellAccel = getTotalItemSpellAccel(purchaserInfoDto) + championInfoDto.getSpellAccel();

        return ChampionAccelInfoDto.builder()
                .summonerName(summonerName)
                .skillAccel(totalSkillAccel)
                .spellAccel(totalSpellAccel)
                .build();
    }

    @Transactional
    public ChampionCoolInfoDto getCalcedCooltimeInfo(String summonerName, int ultLv) {

        if (infoIsUpdated(summonerName)) calculateAndSaveChampionInfo(summonerName, ultLv);
        ChampionInfoDto championInfoDto = championInfoRepo
                .findBySummonerName(summonerName)
                .orElseThrow(CSummonerNoRuneInfoException::new)
                .toInfoDto();

        return ChampionCoolInfoDto.builder()
                .cooltimeD(championInfoDto.getDSpellTime())
                .cooltimeF(championInfoDto.getFSpellTime())
                .cooltimeR(championInfoDto.getRSpellTime())
                .build();
    }
}
