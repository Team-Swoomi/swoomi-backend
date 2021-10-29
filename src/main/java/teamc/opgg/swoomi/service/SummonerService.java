package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SummonerRequestDto;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.repository.SummonerRepo;
@Slf4j
@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepo summonerRepo;

    @Transactional(readOnly = true)
    public SummonerResponseDto findById(Long id) {
        MySummoner summoner = summonerRepo.findById(id)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true)
    public SummonerResponseDto findBySummonerId(String summonerId) {
        MySummoner summoner = summonerRepo.findBySummonerId(summonerId)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true)
    public SummonerResponseDto findBySummonerName(String summonerName) {
        MySummoner summoner = summonerRepo.findBySummonerName(summonerName)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true)
    public SummonerResponseDto findFirstSummonerName(String summonerName) {
        MySummoner summoner = summonerRepo.findFirstBySummonerName(summonerName)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    public SummonerResponseDto findAccountId(String accountId) {
        MySummoner summoner = summonerRepo.findByAccountId(accountId)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }
}
