package teamc.opgg.swoomi.repository;

import lombok.Getter;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Getter
public class MysticItemRepo {
    private static MysticItemRepo mysticRepo;
    public HashSet<String> mysticItemSet = new HashSet<>();

    private MysticItemRepo() {
    }

    public static MysticItemRepo getInstance() {
        if (mysticRepo == null) {
            mysticRepo = new MysticItemRepo();
            mysticRepo.mysticItemSet.add("터보 화공 탱크");
            mysticRepo.mysticItemSet.add("드락사르의 황혼검");
            mysticRepo.mysticItemSet.add("리안드리의 고뇌");
            mysticRepo.mysticItemSet.add("밤의 수확자");
            mysticRepo.mysticItemSet.add("선혈포식자");
            mysticRepo.mysticItemSet.add("슈렐리아의 군가");
        }
        return mysticRepo;
    }
}
