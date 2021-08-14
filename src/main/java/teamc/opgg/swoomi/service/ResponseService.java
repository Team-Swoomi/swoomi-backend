package teamc.opgg.swoomi.service;

import org.springframework.stereotype.Service;
import teamc.opgg.swoomi.entity.response.CommonResponse;
import teamc.opgg.swoomi.entity.response.CommonResult;
import teamc.opgg.swoomi.entity.response.ListResult;
import teamc.opgg.swoomi.entity.response.SingleResult;

import java.util.List;

@Service
public class ResponseService {

    // 단일 결과
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 복수 결과
    public <T> ListResult<T> getListResult(List<T> data) {
        ListResult<T> result = new ListResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        setFailResult(result, code, msg);
        return result;
    }


    // 성공 결과 매핑
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 실패 결과 매핑
    private void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }
}
