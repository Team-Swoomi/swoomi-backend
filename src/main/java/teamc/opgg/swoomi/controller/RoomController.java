package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import teamc.opgg.swoomi.dto.RoomDto;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.entity.response.SingleResult;
import teamc.opgg.swoomi.service.ResponseService;
import teamc.opgg.swoomi.service.RoomService;

@Controller
@RequestMapping("/v1/room")
@Api(tags = {"3. Room Creation and Join"})
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private ResponseService responseService;

    /**
     * 방장 방 생성
     *
     * @param body
     * @return dto
     */
    @PostMapping("/")
    @ResponseBody
    @ApiOperation(value = "방 생성")
    public CommonResult createRoom(@ApiParam(value = "Room 정보", required = true) @RequestBody RoomDto body) {
        roomService.createRoom(body);
        return responseService.getSuccessResult();
    }

    /**
     * RoomNumber로 찾아서 게임 시작 여부 알려주기
     * @param roomNumber
     * @return dto
     */
    @GetMapping("/{roomNumber}")
    @ResponseBody
    @ApiOperation(value = "방 찾아서 게임 시작 여부 리턴")
    public SingleResult<RoomDto> findRoom(@ApiParam(value = "Room 넘버", required = true) @PathVariable String roomNumber) {
        RoomDto dto = new RoomDto();
        dto = roomService.findRoom(roomNumber);
        return responseService.getSingleResult(dto);
    }
}
