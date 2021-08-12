package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.CommonDto;
import teamc.opgg.swoomi.dto.RoomDto;
import teamc.opgg.swoomi.service.RoomService;
import teamc.opgg.swoomi.util.ConstantStore;

@Controller
@RequestMapping("/v1/room")
@Api(tags = {"Room 생성 및 입장 관련 API"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 방장 방 생성
     *
     * @param body
     * @return dto
     */
    @PostMapping("/")
    @ResponseBody
    @ApiOperation(value = "방 생성", response = CommonDto.class)
    public CommonDto createRoom(@ApiParam(value = "Room 정보", required = true) @RequestBody RoomDto body) {
        CommonDto dto = new CommonDto();
        try {
            roomService.createRoom(body);
            dto.sucess();
        } catch (Exception e) {
            e.printStackTrace();
            dto.failed(e.getMessage());
        } finally {
            return dto;
        }
    }

    /**
     * RoomNumber로 찾아서 게임 시작 여부 알려주기
     * @param roomNumber
     * @return dto
     */
    @GetMapping("/{roomNumber}")
    @ResponseBody
    @ApiOperation(value = "방 찾아서 게임 시작 여부 리턴", response = RoomDto.class)
    public RoomDto findRoom(@ApiParam(value = "Room 넘버", required = true) @PathVariable String roomNumber) {
        RoomDto dto = new RoomDto();
        try {
            dto = roomService.findRoom(roomNumber);
            dto.sucess();
        } catch (Exception e) {
            e.printStackTrace();
            dto.failed(e.getMessage());
        } finally {
            return dto;
        }
    }
}
