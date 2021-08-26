package teamc.opgg.swoomi.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamc.opgg.swoomi.advice.exception.*;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.ResponseService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

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
        log.error(e.toString());
        return responseService.getFailResult(
                ErrorCode.UnDefinedError.getCode(),
                ErrorCode.UnDefinedError.getMsg()
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
}
