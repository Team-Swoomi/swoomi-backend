package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.spectator.Player;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotInGameException;
import teamc.opgg.swoomi.dto.ChampionInfoDto;
import teamc.opgg.swoomi.dto.ItemDto;
import teamc.opgg.swoomi.dto.PlayerDto;
import teamc.opgg.swoomi.entity.ChampionItem;
import teamc.opgg.swoomi.repository.ChampionHasItemRepo;
import teamc.opgg.swoomi.repository.ChampionItemRepository;
import teamc.opgg.swoomi.repository.MatchTeamCodeSummonerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OppositeInfoService {

    private final CommonService commonService;
    private final ChampionItemRepository championItemRepository;
    private final ChampionInfoService championInfoService;
    private final MatchTeamCodeSummonerRepository matchTeamCodeSummonerRepository;
    private final MatchService matchService;
    private final OriannaService oriannaService;
    public List<PlayerDto> getOpData(String summonerName) {
        Summoner summoner = Orianna.summonerNamed(summonerName).withRegion(Region.KOREA).get();
        if (!summoner.exists()) {
            log.info("NO SUMMONER NAMED : " + summonerName);
            throw new CSummonerNotFoundException();
        }
        if (!matchService.getMatchStatus(oriannaService.SummonerFindByNameAndSave(summonerName).getSummonerId()).isMatchStatus()) {
            log.info("SUMMONER '" + summonerName + "' NOT IN GAME");
            throw new CSummonerNotInGameException();
        }

        // 1. 상대팀 구하기
        SearchableList<Player> sList = summoner.getCurrentMatch().getParticipants();
        Player tempPlayer = sList.find((playerName) -> playerName.getSummoner().getName().equals(summonerName));
        String teamId = tempPlayer.getTeam().toString();

        // 2. 상대팀 멤버 구하기
        SearchableList<Player> opList = sList.filter((player) -> !player.getTeam().toString().equals(teamId));

        commonService.initChampionNameHasItem();

        return getPlayerDtos(opList);
    }

    public List<PlayerDto> getPlayerDtos(SearchableList<Player> opList) {

        List<PlayerDto> playerDtos = new ArrayList<>();

        for (Player p : opList) {
            String championNameForDto = p.getChampion().getName();
            String championName = championNameForDto.replace(" ", "");
            Set<String> set = new HashSet<>();
            List<ItemDto> list = new ArrayList<>();
            Optional<List<ChampionItem>> optionalChampionItems =
                    championItemRepository.findAllByChampionName(championName);

            new Thread(
                    () -> championInfoService.calculateAndSaveChampionInfo(p.getSummoner().getName(), 1)
            ).start();

            if (ChampionHasItemRepo.getInstance().getChampionSet().contains(championName)
                    && optionalChampionItems.isPresent()) {
                list = optionalChampionItems.get()
                        .stream()
                        .map((e) -> {
                                    if (!set.contains(e.getItemName())) {
                                        set.add(e.getItemName());
                                        return e.toDto();
                                    } else {
                                        return null;
                                    }
                                }
                        )
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
            } else {
                list.add(
                        ItemDto.builder()
                                .name("명석함의 아이오니아 장화")
                                .englishName("Ionian Boots of Lucidity")
                                .skillAccel("20")
                                .src("https://opgg-static.akamaized.net/images/lol/item/3158.png?image=q_auto:best&v=1628647804")
                                .build()
                );
            }
            PlayerDto dto = PlayerDto.builder().summonerName(p.getSummoner().getName())
                    .championName(championNameForDto)
                    .championImgUrl(p.getChampion().getImage().getURL())
                    .ultImgUrl(p.getChampion().getSpells().get(3).getImage().getURL())
                    .spellDName(p.getSummonerSpellD().getName())
                    .spellFName(p.getSummonerSpellF().getName())
                    .spellDImgUrl(p.getSummonerSpellD().getImage().getURL())
                    .spellFImgUrl(p.getSummonerSpellF().getImage().getURL())
                    .frequentItems(list)
                    .build();

            playerDtos.add(dto);
        }
        return playerDtos;
    }

    public List<PlayerDto> getOpDataMatchTeamCode(String matchTeamCode) {
        String summonerName = matchTeamCodeSummonerRepository.findFirstByMatchTeamCode(matchTeamCode)
                .get()
                .getSummonerName();
        return getOpData(summonerName);
    }
}
