package teamc.opgg.swoomi.repository;

import lombok.Getter;

import java.util.HashSet;

@Getter
public class LegendaryItemRepo {
    private static LegendaryItemRepo legendaryItemRepo = null;
    public HashSet<String> legendaryItemSet = new HashSet<>();

    private LegendaryItemRepo() {
    }

    public static LegendaryItemRepo getInstance() {
        if (legendaryItemRepo == null) {
            legendaryItemRepo = new LegendaryItemRepo();
            legendaryItemRepo.legendaryItemSet.add("그림자 검");
            legendaryItemRepo.legendaryItemSet.add("독사의 송곳니");
            legendaryItemRepo.legendaryItemSet.add("밤의 끝자락");
            legendaryItemRepo.legendaryItemSet.add("요우무의 유령검");
            legendaryItemRepo.legendaryItemSet.add("세릴다의 원한");
            legendaryItemRepo.legendaryItemSet.add("화공 펑크 사슬검");
            legendaryItemRepo.legendaryItemSet.add("수호 천사");
            legendaryItemRepo.legendaryItemSet.add("맬모셔스의 아귀");
            legendaryItemRepo.legendaryItemSet.add("선체파괴자");
            legendaryItemRepo.legendaryItemSet.add("마나무네");
            legendaryItemRepo.legendaryItemSet.add("은빛 여명");
            legendaryItemRepo.legendaryItemSet.add("스테락의 도전");
            legendaryItemRepo.legendaryItemSet.add("마법사의 최후");
            legendaryItemRepo.legendaryItemSet.add("죽음의 무도");
            legendaryItemRepo.legendaryItemSet.add("칠흑의 양날 도끼");
            legendaryItemRepo.legendaryItemSet.add("몰락한 왕의 검");
            legendaryItemRepo.legendaryItemSet.add("모렐로노미콘");
            legendaryItemRepo.legendaryItemSet.add("굶주린 히드라");
            legendaryItemRepo.legendaryItemSet.add("거대한 히드라");
            legendaryItemRepo.legendaryItemSet.add("메자이의 영혼약탈자");
            legendaryItemRepo.legendaryItemSet.add("밴시의 장막");
            legendaryItemRepo.legendaryItemSet.add("존야의 모래시계");
            legendaryItemRepo.legendaryItemSet.add("공허의 지팡이");
            legendaryItemRepo.legendaryItemSet.add("대천사의 지팡이");
            legendaryItemRepo.legendaryItemSet.add("리치베인");
            legendaryItemRepo.legendaryItemSet.add("내셔의 이빨");
            legendaryItemRepo.legendaryItemSet.add("라일라이의 수정홀");
            legendaryItemRepo.legendaryItemSet.add("지평선의 초점");
            legendaryItemRepo.legendaryItemSet.add("우주의 추진력");
            legendaryItemRepo.legendaryItemSet.add("악마의 포옹");
            legendaryItemRepo.legendaryItemSet.add("라바돈의 죽음모자");
            legendaryItemRepo.legendaryItemSet.add("필멸자의 운명");
            legendaryItemRepo.legendaryItemSet.add("고속 연사포");
            legendaryItemRepo.legendaryItemSet.add("유령 무희");
            legendaryItemRepo.legendaryItemSet.add("루난의 허리케인");
            legendaryItemRepo.legendaryItemSet.add("폭풍갈퀴");
            legendaryItemRepo.legendaryItemSet.add("구인수의 격노검");
            legendaryItemRepo.legendaryItemSet.add("정수 약탈자");
            legendaryItemRepo.legendaryItemSet.add("도미닉 경의 인사");
            legendaryItemRepo.legendaryItemSet.add("헤르메스의 시미터");
            legendaryItemRepo.legendaryItemSet.add("징수의 총");
            legendaryItemRepo.legendaryItemSet.add("무한의 대검");
            legendaryItemRepo.legendaryItemSet.add("피바라기");
            legendaryItemRepo.legendaryItemSet.add("나보리 신속검");
            legendaryItemRepo.legendaryItemSet.add("구원");
            legendaryItemRepo.legendaryItemSet.add("기사의 맹세");
            legendaryItemRepo.legendaryItemSet.add("미카엘의 축복");
            legendaryItemRepo.legendaryItemSet.add("불타는 향로");
            legendaryItemRepo.legendaryItemSet.add("경계의 와드석");
            legendaryItemRepo.legendaryItemSet.add("흐르는 물의 지팡이");
            legendaryItemRepo.legendaryItemSet.add("화학공학 부패기");
            legendaryItemRepo.legendaryItemSet.add("지크의 융합");
            legendaryItemRepo.legendaryItemSet.add("얼어붙은 심장");
            legendaryItemRepo.legendaryItemSet.add("증오의 사슬");
            legendaryItemRepo.legendaryItemSet.add("심연의 가면");
            legendaryItemRepo.legendaryItemSet.add("가시 갑옷");
            legendaryItemRepo.legendaryItemSet.add("란두인의 예언");
            legendaryItemRepo.legendaryItemSet.add("정령의 형상");
            legendaryItemRepo.legendaryItemSet.add("망자의 갑옷");
            legendaryItemRepo.legendaryItemSet.add("대자연의 힘");
            legendaryItemRepo.legendaryItemSet.add("워모그의 갑옷");
            legendaryItemRepo.legendaryItemSet.add("가고일 돌갑옷");
        }
        return legendaryItemRepo;
    }
}
