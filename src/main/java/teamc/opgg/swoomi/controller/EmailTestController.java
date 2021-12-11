package teamc.opgg.swoomi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamc.opgg.swoomi.advice.exception.CEmailSendException;

@RestController
@RequestMapping("/email")
@Api(tags = {"8. Email"})
public class EmailTestController {

    @PostMapping("/test")
    @ApiOperation(value = "(주의) 이메일이 전송됩니다.", notes = "에러 발생시 이메일 전송 테스트")
    public void sendEmailTest() {
        throw new CEmailSendException();
    }
}
