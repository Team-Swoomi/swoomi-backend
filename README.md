![Untitled](https://i.imgur.com/0AVIRFa.png)

# 개발내용

![다이어그램](https://user-images.githubusercontent.com/42247724/134616777-cd41d48e-9b3d-42ec-9cd2-88c7e77a11e5.png)
![entityManagerFactory(EntityManagerFactoryBuilder)](https://user-images.githubusercontent.com/42247724/134616954-68de5fff-ee8a-42ad-915e-452defaf6c70.png)

### [API 문서](https://backend.swoomi.me/swagger-ui.html#!/5._%EC%B1%94%ED%94%BC%EC%96%B8_%EC%BF%A8%EA%B0%90%2F%EC%BF%A8%ED%83%80%EC%9E%84_%EC%A0%95%EB%B3%B4/findChampionCalcedCooltimeInfoUsingGET)

### [GITHUB](https://github.com/OPGG-HACKTHON/web-team-c-backend)

### 개발일지

- Spring Boot, Gradle, Mysql을 이용해서 REST API 구현
- Swagger를 통한 API 문서화
- RestControllerAdvice, Exception Handler를 통해 에러 통합 핸들링
- Java Thread를 이용한 비동기 처리
- STOMP+SockJS를 이용한 Web Socket 사용
- AWS EC2, RDS, Route53을 이용해서 배포 및 DNS 적용
- Jenkins 를 이용한 CI/CD 적용

### 구현목록

- 소환사 명 입력시 게임 중 여부 확인
- 스우미 서비스 공유를 위한 QR 생성
- 게임 여부 확인 시 Riot API를 통해 스펠, 궁극기, 프로필 아이콘 등 각종 정보 세팅
- 게임 시작 시 상대편 5명의 정보를 비동기로 처리해서 업데이트
- 게임 시작 후 아이템 구매, 용 처치, 궁극기 레벨 조작 시 소켓 통신으로 동기화

# SWOOMI 소개

:link: [스우미](https://swoomi.me/)

## 코드

- [프론트](https://github.com/OPGG-HACKTHON/web-c-client).
- [서버](https://github.com/OPGG-HACKTHON/web-team-c-backend).
- [크롤러](https://github.com/OPGG-HACKTHON/web-c-crawler).

## 목표

> `편리`하게, `정확한` 스펠체크로 `티어 상승`에 기여한다!

## 지원

1. 안드로이드 모바일 환경을 권장합니다!
2. PC의 Chromium을 사용하는 브라우저에서 스우미가 제공하는 모든 서비스를 이용할 수 있습니다.

서비스별 지원 가능 브라우저

|               기능                | Chrome | Edge |       Safari       |       Naver Whale       |
| :-------------------------------: | :----: | :--: | :----------------: | :---------------------: |
|             음성 인식             |  지원  | 지원 | 14.1 이상부터 지원 | 지원 불가 (인식률 낮음) |
|             음성 알림             |  지원  | 지원 | 14.1 이상부터 지원 |          지원           |
| 검색 페이지 하단 고정된 검색 버튼 |  지원  | 지원 |     지원 불가      |          지원           |

## 특징

1. `룬`, `아이템`, `바람용`을 모두 고려한 `정확한` 스펠 시간
2. 팀원들과의 `실시간` 동기화를 통한 `정확`하고 `빠른` 스펠 체크
3. `직관적`이고 `간편한` UI, UX로 게임에 집중하며 스펠 체크
4. `PC`와 `모바일`, 어디서든 이용할 수 있습니다.

## **기능별 안내**

### 스펠 체크

- **클릭**

  1. **원터치로 스펠을 간편하게 체크할 수 있습니다.**

  ![Untitled](https://i.imgur.com/ybgumc9.png)

  1. **잘못 클릭하는 경우를 대비하여, 스펠 체크 후 특정 시간이 흐르면 스펠 체크 박스가 잠금상태로 변합니다.**

  ![Untitled](https://i.imgur.com/mioUODg.png)

  1. **스펠 사용 후 시간이 흐른 뒤에도 정확히 체크할 수 있도록 세부적인 시간 조정 기능을 제공합니다.**

- **음성**

  1. **'{챔피언 이름} 노 {스펠 이름}' 을 말하여 스펠을 더욱 간편하게 체크할 수 있습니다.**

     ex) 🎙️ "잭스 노텔", "갈리오 노플", "럭스 노궁"

     → 현재는 PC와 안드로이드의 Chromium, Mac safari 최신 사양에서 지원합니다.

### 스펠 안내

- **화면의 상단에서 한 눈에 궁, 스펠의 잔여시간 및 On / Off 여부를 확인할 수 있습니다.**

  → 롤에서의 궁, 스펠 표시와 같은 방식으로 제공하여, 직관적입니다.

  ![Untitled](https://i.imgur.com/TE9rBrA.png)

- 1**0초 전, On 두가지 경우에 음성으로 스펠 상태를 안내합니다.**

  ex) 🔊 "갈리오 플 10초 전", "갈리오 궁 온"

  → 현재는 PC와 안드로이드의 Chromium에서 지원합니다.

### 정확한 스펠 제공

- **룬**

  1. **라이엇 API를 통해 스킬쿨 관련 룬을 파악합니다.**

- **아이템**

  1. **챔피언, 라인별로 자주가는 아이템을 제공하여 빠르게 구매 아이템을 클릭할 수 있습니다.**

  ![Untitled](https://i.imgur.com/11ay8Mv.png)

  b. **남들과 다른 힙한 상대팀을 대비하여, 전체 아이템 검색을 통해 구매 아이템 추가가 가능합니다.**

  ![Untitled](https://i.imgur.com/bchf3Tw.png)

- **바람용**

  1. **Up, Down 버튼을 통해 상대팀이 먹은 바람용 개수를 0 - 4까지 지정할 수 있습니다.**

  ![Untitled](https://i.imgur.com/kGzT2D5.png)

위에서 모은 정보를 토대로 정확한 스킬 쿨타임을 계산하여 제공합니다.

### 편리한 이용

- 챔피언 라인을 추정하여 챔피언 순서를 배치합니다.
- 모든 페이지에서 음성/텍스트 다국어를 지원합니다. ( 영어, 한국어 )
- AOD(Always On Display)를 지원하여 게임 중 언제든 스펠을 확인하고 체크할 수 있습니다.

## **페이지별 안내**

`모든 페이지는 영어와 한국어를 지원합니다.`

1. **서치 페이지**

   - 소한사명을 입력하여 검색합니다.
   - 게임 중이지 않으면 대기 페이지로 이동합니다.
   - 게임 중이면 게임방 페이지로 이동합니다.

     ![Untitled](https://i.imgur.com/84XPmRY.png)

1. **대기 페이지**

   - 공유하기로 팀원에게 링크를 제공할 수 있습니다.
   - 링크를 받은 팀원은 링크를 통해 공유 페이지로 이동합니다.
   - 대기 중, 게임이 시작하면 게임 페이지로 자동으로 이동합니다.

     ![Untitled](https://i.imgur.com/ke1YGKz.png)

1. **공유 페이지**

   - PC와 Mobile 중 하나를 선택하여 스우미를 이용할 수 있습니다.
   - PC를 선택한 경우 바로 웹 페이지로 이동합니다.
   - Mobile을 선택한 경우 QR코드를 통해 모바일에서 웹 페이지로 이동합니다.

     ![Untitled](https://i.imgur.com/E6tR21M.png)

1. **게임방 페이지**

   - 상대팀의 룬, 아이템, 바람용 수를 고려하여 `정확한` 스펠 쿨타임을 제공합니다.
   - 실시간으로 팀원과 상대팀의 `스펠 사용 시간`, `구매한 아이템`, `먹은 바람용 수`를 공유할 수 있습니다.
   - 게임에 집중할 수 있도록 `직관적`이고 `간편한` UI, UX를 제공합니다.
   - 스펠 사용 시간을 클릭하여 상대팀 스펠 시간을 잴 수 있습니다.
   - `정확한` 시간 카운트를 위해 세부적인 초 조절 기능을 제공합니다.
   - 클릭 없이 음성으로 `간편`하게 상대팀 스펠 시간을 잴 수 있습니다. ex) 🎙️ "베인 노플"
   - 자체적으로 게임 내 챔피언 라인을 예상하여 순서대로 챔피언을 나열합니다.
   - Drag & Drop으로 챔피언 순서를 변경할 수 있습니다.
   - 챔피언 클릭을 통해 해당 챔피언으로 바로 이동할 수 있습니다.
   - 자체 데이터로 챔피언별, 라인별 자주가는 아이템을 우선적으로 제공하여 `빠르고` `편하게` 아이템 구매가 가능하도록 지원하고 있습니다.
   - 컨셉이 짙은 힙한 상대팀을 대비하여, 스우미가 추천하는 아이템에 상대팀이 구매한 아이템이 없더라도 검색을 통해 구매할 수 있습니다.
   - 화면이 꺼져 게임에 방해되지 않도록 PC, Mobile 모두 AOD(Always On Display)를 지원합니다.

     ![Untitled](https://i.imgur.com/0GXuNn0.png)

## **개발 예정 기능**

- Riot에서 제공하지 않는 사용자 인증 서비스를 스우미만의 아이디어로 Riot과 연동된 로그인 서비스 구현

  → 악성 유저 방지를 통한 정확한 시간 측정

- 튜토리얼 및 안내 서비스 제공
- 사용자가 원하는 대로 커스터마이징이 가능한 웹 UI 지원
- 앱 서비스를 런칭해 모든 환경에서 서비스를 누릴 수 있도록 지원
- 아이템 재사용 대기시간 계산 (가엔 / 존야 / 퀵실 등...)

## 팀원

- 팀장 : 강현구 (프론트엔드)
- 레이아웃 장인 : 문종영 (프론트엔드)
- 프론트 버그 카운터 : 구승효 (프론트엔드)
- API 공장 : 최운식 (백엔드)
- 서버 버그 담당 일진 : 한대건 (백엔드)
- 스우미의 어머니 : 임효연 (디자이너)

## 저작권
Copyright 2021. Team-Swoomi All Rights Reserved.

이 저작물은 저작자표시-비영리-변경금지 4.0 국제 라이선스에 따라 이용할 수 있습니다.
This work is licensed under a Attribution-NonCommercial-NoDerivatives 4.0 International License.
모든 제품명 및 상표는 해당 소유주의 자산입니다.
All product names and trademarks are the property of their respective owners.
저작권 위배 또는 잘못된 내용이 있으면 부담없이 메일 부탁드립니다.
If there are copyright issues, please contact swoomi21@gmail.com. 
