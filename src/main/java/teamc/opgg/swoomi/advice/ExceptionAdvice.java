package teamc.opgg.swoomi.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamc.opgg.swoomi.advice.exception.CSummonerNotFoundException;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.service.ResponseService;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final ResponseService responseService;

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
        return responseService.getFailResult(
                ErrorCode.UnDefinedError.getCode(), ErrorCode.UnDefinedError.getMsg()
        );
    }

}
