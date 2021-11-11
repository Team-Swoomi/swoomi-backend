package teamc.opgg.swoomi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.dto.SummonerResponseDto;
import teamc.opgg.swoomi.entity.MySummoner;
import teamc.opgg.swoomi.redis.CacheKey;
import teamc.opgg.swoomi.repository.MySummonerRepo;
@Slf4j
@Service
@RequiredArgsConstructor
public class SummonerService {

    private final MySummonerRepo mySummonerRepo;

    @Transactional(readOnly = true)
    public SummonerResponseDto findById(Long id) {
        MySummoner summoner = mySummonerRepo.findById(id)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true)
    public SummonerResponseDto findBySummonerId(String summonerId) {
        MySummoner summoner = mySummonerRepo.findBySummonerId(summonerId)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CacheKey.SUMMONER)
    public SummonerResponseDto findBySummonerName(String summonerName) {
        MySummoner summoner = mySummonerRepo.findBySummonerName(summonerName)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Cacheable(value = CacheKey.SUMMONER)
    public SummonerResponseDto findFirstSummonerName(String summonerName) {
        MySummoner summoner = mySummonerRepo.findFirstBySummonerName(summonerName)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }

    public SummonerResponseDto findAccountId(String accountId) {
        MySummoner summoner = mySummonerRepo.findByAccountId(accountId)
                .orElseThrow(CSummonerNotFoundException::new);
        return summoner.toDto();
    }
}
