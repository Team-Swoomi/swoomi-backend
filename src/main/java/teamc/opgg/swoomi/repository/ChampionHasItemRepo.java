package teamc.opgg.swoomi.repository;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import teamc.opgg.swoomi.entity.ChampionItem;

import java.util.HashSet;

@Getter
public class ChampionHasItemRepo {

    private static final ChampionHasItemRepo championHasItemRepo = new ChampionHasItemRepo();
    private final HashSet<String> championSet = new HashSet<>();

    private ChampionHasItemRepo(){}

    public static ChampionHasItemRepo getInstance() {
        return championHasItemRepo;
    }
}
