package teamc.opgg.swoomi.service;

import com.merakianalytics.orianna.Orianna;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.entity.ChampionInfo;
import teamc.opgg.swoomi.repository.ChampionInfoRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChampionInfoService {

    private final ChampionInfoRepo championInfoRepo;
    

    public int getMainPerksSkillAccel(){
        return 0;
    }

    public int getFragmentPerksSkillAccel() {
        return 0;
    }

    public int getMainPerksSpellAccel(){
        return 0;
    }

    public int getFragmentPerksSpellAccel() {
        return 0;
    }
}
