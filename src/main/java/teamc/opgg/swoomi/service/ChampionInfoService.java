package teamc.opgg.swoomi.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.spectator.CurrentMatch;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNoRuneInfoException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.*;
import teamc.opgg.swoomi.entity.ChampionInfo;
import teamc.opgg.swoomi.entity.CloudDragonCount;
import teamc.opgg.swoomi.repository.ChampionInfoRepo;
import teamc.opgg.swoomi.repository.CloudDragonRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChampionInfoService {

    private final Gson gson;
    private final MatchService matchService;
    private final ChampionInfoRepo championInfoRepo;
    private final ItemPurchaseService itemPurchaseService;
    private final CloudDragonRepository cloudDragonRepository;

    public Player getPlayer(String summonerName, String matchTeamCode) {

        log.info("GET PLAYER : ["+summonerName+"]");

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
        return player;
    }

    private boolean infoIsUpdated(String summonerName) {
        if (championInfoRepo.findBySummonerName(summonerName).isPresent()) {
            return championInfoRepo.findBySummonerName(summonerName).get().getUpdated();
        }
        return true;
    }

    public ChampionAccelInfoDto getInitialRuneInfo(String summonerName, String matchTeamCode) {
        Player player = getPlayer(summonerName, matchTeamCode);

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

    public ChampionCoolInfoDto getInitialCooltimeInfo(String summonerName, String matchTeamCode, int ultLevel) {

        if (ultLevel < 0 || ultLevel > 3) ultLevel = 1;

        Player player = getPlayer(summonerName, matchTeamCode);
        Double cooldownDSpell = player.getSummonerSpellD().getCooldowns().get(0);
        Double cooldownFSpell = player.getSummonerSpellF().getCooldowns().get(0);
        String spellDName = player.getSummonerSpellD().getName();
        String spellFName = player.getSummonerSpellF().getName();
        Double cooldownRSpell = player.getChampion().getSpells().get(3).getCooldowns().get(0);
        if (ultLevel > 1) {
            cooldownRSpell = player.getChampion().getSpells().get(3).getCooldowns().get(ultLevel - 1);
        }

//        궁 0레벨 - 420초
//        궁 1레벨 - 358초
//        궁 2레벨 - 296초
//        궁 3레벨 - 235초

        if (cooldownDSpell == 0 && spellDName.equals("순간이동")) {
            cooldownDSpell = setTeleportCooldown(ultLevel, cooldownDSpell);
        } else if (cooldownFSpell == 0 && spellFName.equals("순간이동")) {
            cooldownFSpell = setTeleportCooldown(ultLevel, cooldownFSpell);
        }

        return ChampionCoolInfoDto.builder()
                .cooltimeD(cooldownDSpell)
                .spellDName(spellDName)
                .cooltimeF(cooldownFSpell)
                .spellFName(spellFName)
                .cooltimeR(cooldownRSpell)
                .build();
    }

    private Double setTeleportCooldown(int ultLevel, Double spellCooldown) {
        switch (ultLevel) {
            case 0 : {
                spellCooldown = 420.;
                break;
            }
            case 1: {
                spellCooldown = 358.;
                break;
            }
            case 2: {
                spellCooldown = 296.;
                break;
            }
            case 3: {
                spellCooldown = 235.;
                break;
            }
        }
        return spellCooldown;
    }

    @Transactional
    @Synchronized
    public ChampionInfoDto calculateAndSaveChampionInfo(String summonerName, int ultLevel) {
        String matchTeamCode = matchService.getMatchTeamCode(summonerName).getMatchTeamCode();
        String myCode = matchService.getMyMatchTeamCodeByEnemy(matchTeamCode);
        Optional<ChampionInfo> championInfo =
                championInfoRepo.findBySummonerName(summonerName);

        if (championInfo.isPresent() && championInfo.get().getMatchTeamCode() != null
                && championInfo.get().getMatchTeamCode().equals(myCode)
                && !infoIsUpdated(summonerName)) {
            ChampionInfo info = championInfo.get();

            double skillAccel = info.getSkillAccel();
            Optional<CloudDragonCount> cloudDragonCount =
                    cloudDragonRepository.findCloudDragonCountByMatchTeamCode(myCode);

            if (cloudDragonCount.isPresent()) {
                skillAccel += cloudDragonCount.get().getDragonCount() * 12;
            }

            ChampionCoolInfoDto cooltimeInfo = getInitialCooltimeInfo(summonerName, matchTeamCode, ultLevel);
            Double cooltimeR = cooltimeInfo.getCooltimeR();

            double skillCooldownPercent = (100 - (double) 100 * (skillAccel / (100 + skillAccel))) / 100;
            double cooltimeCalcedR = Math.round(cooltimeR * skillCooldownPercent);

            championInfo.get().setDSpellTime(cooltimeInfo.getCooltimeD());
            championInfo.get().setFSpellTime(cooltimeInfo.getCooltimeF());
            championInfo.get().setRSpellTime(cooltimeCalcedR);
            return championInfo.get().toInfoDto();
        }

        Player player = getPlayer(summonerName, matchTeamCode);
        String championName = player.getChampion().getName().replaceAll(" ", "");

        ItemPurchaserInfoDto purchaserInfoDto = ItemPurchaserInfoDto.builder()
                .championName(championName)
                .summonerName(summonerName)
                .matchTeamCode(myCode)
                .build();

        ChampionAccelInfoDto initialRuneInfo = getInitialRuneInfo(summonerName, matchTeamCode);
        int finalSkillAccel = initialRuneInfo.getSkillAccel() + getTotalItemSkillAccel(purchaserInfoDto);
        int finalSpellAccel = initialRuneInfo.getSpellAccel() + getTotalItemSpellAccel(purchaserInfoDto);

        int cloudSkillAccel = 0;
        Optional<CloudDragonCount> cloudDto = cloudDragonRepository
                .findCloudDragonCountByMatchTeamCode(myCode);
        if (cloudDto.isPresent()) {
            cloudSkillAccel += cloudDto.get().getDragonCount() * 12;
        }

        log.info("FINAL SKILL ACCEL : " + finalSkillAccel + cloudSkillAccel);
        log.info("FINAL SPELL ACCEL : " + finalSpellAccel);

        ChampionCoolInfoDto championCoolInfoDto = getInitialCooltimeInfo(summonerName, matchTeamCode, ultLevel);
        Double cooltimeD = championCoolInfoDto.getCooltimeD();
        Double cooltimeF = championCoolInfoDto.getCooltimeF();
        String spellDName = championCoolInfoDto.getSpellDName();
        String spellFName = championCoolInfoDto.getSpellFName();
        Double cooltimeR = championCoolInfoDto.getCooltimeR();

        int tmpTotalSkillAccel = finalSkillAccel + cloudSkillAccel;
        double spellCooldownPercent = (100 - (double) 100 * ((double) finalSpellAccel / (double) (100 + finalSpellAccel))) / 100;
        double skillCooldownPercent = (100 - (double) 100 * ((double) tmpTotalSkillAccel / (double) (100 + tmpTotalSkillAccel))) / 100;

        double cooltimeCalcedD = Math.round(cooltimeD * spellCooldownPercent);
        double cooltimeCalcedF = Math.round(cooltimeF * spellCooldownPercent);
        double cooltimeCalcedR = Math.round(cooltimeR * skillCooldownPercent);

        log.info("FINAL COOL TIME D : " + cooltimeCalcedD);
        log.info("FINAL COOL TIME F : " + cooltimeCalcedF);
        log.info("FINAL COOL TIME R : " + cooltimeCalcedR);

        ChampionInfoDto championInfoDto = ChampionInfoDto.builder()
                .summonerName(summonerName)
                .championName(championName)
                .matchTeamCode(myCode)
                .dSpellTime(cooltimeCalcedD)
                .fSpellTime(cooltimeCalcedF)
                .rSpellTime(cooltimeCalcedR)
                .skillAccel(finalSkillAccel)
                .spellAccel(finalSpellAccel)
                .dSpellName(spellDName)
                .fSpellName(spellFName)
                .build();

        if (championInfo.isPresent()) {
            ChampionInfo info = championInfo.get();
            info.setDSpellTime(cooltimeCalcedD);
            info.setFSpellTime(cooltimeCalcedF);
            info.setRSpellTime(cooltimeCalcedR);
            info.setSkillAccel(finalSkillAccel);
            info.setSpellAccel(finalSpellAccel);
            info.setMatchTeamCode(myCode);
            info.setUpdated(false);
        } else {
            ChampionInfo info = championInfoDto.toEntity();
            info.setCountLegendary(0);
            info.setHasMystic(false);
            info.setUpdated(false);
            championInfoRepo.save(info);
        }

        return championInfoDto;
    }

    public ChampionCoolInfoDto getCalcedCooltimeInfo(String summonerName, int ultLv) {

        ChampionInfoDto championInfoDto = calculateAndSaveChampionInfo(summonerName, ultLv);

        return ChampionCoolInfoDto.builder()
                .cooltimeD(championInfoDto.getDSpellTime())
                .cooltimeF(championInfoDto.getFSpellTime())
                .cooltimeR(championInfoDto.getRSpellTime())
                .spellDName(championInfoDto.getDSpellName())
                .spellFName(championInfoDto.getFSpellName())
                .build();
    }
}
