package teamc.opgg.swoomi.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamc.opgg.swoomi.advice.exception.*;
import teamc.opgg.swoomi.dto.MailDto;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.MailService;
import teamc.opgg.swoomi.service.ResponseService;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;
    private final MailService mailService;

    @Value("${notify_to.emails}")
    private String[] RECEIVERS;

    @ExceptionHandler(CSummonerNotInGameException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult summonerNotInGameException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                ErrorCode.SummonerNotInGameException.getCode(),
                ErrorCode.SummonerNotInGameException.getMsg()
        );
    }

    @ExceptionHandler(CRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult roomNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                ErrorCode.RoomNotFoundException.getCode(),
                ErrorCode.RoomNotFoundException.getMsg()
        );
    }

    @ExceptionHandler(CSummonerNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult summonerNotFoundException(HttpServletRequest request, CSummonerNotFoundException e) {
        return responseService.getFailResult(
                ErrorCode.SummonerNotFoundException.getCode(),
                ErrorCode.SummonerNotFoundException.getMsg()
        );
    }

    @ExceptionHandler(CQrCodeFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult qrCodeFailException(HttpServletRequest request, CQrCodeFailException e) {
        return responseService.getFailResult(
                ErrorCode.QrCodeFailException.getCode(),
                ErrorCode.QrCodeFailException.getMsg()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult otherException(HttpServletRequest request, Exception e) {
        e.printStackTrace();

        StringBuilder log = new StringBuilder()
                .append("REQ URI: ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append("\n\n")
                .append("EXCEPTION: ").append(e).append("\n\n")
                .append("SYS ERR: ").append(System.err);

        mailService.mailSend(MailDto.builder()
                .to(RECEIVERS)
                .sentDate(Date.from(Instant.now()))
                .subject("🚨 SWOOMI NON JUSTIFY EXCEPTION 🚨")
                .text(log.toString())
                .build());

        return responseService.getFailResult(
                ErrorCode.UnDefinedError.getCode(),
                e.getMessage()
        );
    }

    /**
     * -1005
     */
    @ExceptionHandler(CMsgRoomNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult MsgRoomNotFoundException(HttpServletRequest request, CMsgRoomNotFoundException e) {
        return responseService.getFailResult(
                ErrorCode.RoomNotFoundException.getCode(),
                ErrorCode.MsgRoomNotFoundException.getMsg()
        );
    }

    /***
     * -1006
     */
    @ExceptionHandler(CSummonerNoRuneInfoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult noRuneInfoException(HttpServletRequest request, CSummonerNoRuneInfoException e) {
        return responseService.getFailResult(
                ErrorCode.SummonerNoRuneInfoException.getCode(),
                ErrorCode.SummonerNoRuneInfoException.getMsg()
        );
    }

    /***
     * -1007
     */
    @ExceptionHandler(CSummonerNoItemInfoException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult noItemInfoException(HttpServletRequest request, CSummonerNoItemInfoException e) {
        return responseService.getFailResult(
                ErrorCode.SummonerNoItemInfoException.getCode(),
                ErrorCode.SummonerNoItemInfoException.getMsg()
        );
    }
}
