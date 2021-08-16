package teamc.opgg.swoomi.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamc.opgg.swoomi.advice.exception.CRoomNotFoundException;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.ResponseService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

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
    public CommonResult summonerNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(
                ErrorCode.SummonerNotFoundException.getCode(),
                ErrorCode.SummonerNotFoundException.getMsg()
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

}
