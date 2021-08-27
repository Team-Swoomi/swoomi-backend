package teamc.opgg.swoomi.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.MsgRoomDto;
import teamc.opgg.swoomi.entity.MsgRoom;
import teamc.opgg.swoomi.service.MsgService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comm")
public class MsgRoomController {

    private final MsgService msgService;

    @GetMapping("/room")
    public String room(Model model) {
        return "room";
    }

    @ApiOperation(value = "방 입장", notes = "room ID를 통해서 방에 입장합니다.")
    @GetMapping("/room/enter/{roomId}")
    public String roomEnter(
            Model model,
            @ApiParam(value = "방 ID", required = true)
            @PathVariable String roomId) {

        model.addAttribute("roomId", roomId);
        return "roomEnter";
    }

    @ResponseBody
    @GetMapping("/rooms")
    public List<MsgRoomDto> rooms() {
        return msgService.findAllRoom();
    }

    @ResponseBody
    @ApiOperation(value = "방 조회", notes = "room ID를 통해서 방을 조회합니다.")
    @GetMapping("/room/{roomId}")
    public MsgRoomDto roomInfo(
            @ApiParam(value = "방 ID", required = true)
            @PathVariable String roomId) {
        return msgService.findById(roomId);
    }

    @ResponseBody
    @ApiOperation(value = "방 생성", notes = "팀원 중 한명의 이름으로 최초 방을 생성한다.")
    @PostMapping("/room")
    public String createRoom(
            @ApiParam(value = "소환사 명", required = true)
            @RequestParam String summonerName) {
        return msgService.createRoom(summonerName);
    }
}
