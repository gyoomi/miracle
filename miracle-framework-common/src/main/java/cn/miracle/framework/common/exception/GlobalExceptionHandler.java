package cn.miracle.framework.common.exception;

import cn.miracle.framework.common.model.response.CommonCode;
import cn.miracle.framework.common.model.response.ResponseResult;
import cn.miracle.framework.common.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/6/17 0:18
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger lg = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 定义map，配置异常类型所对应的错误代码
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> exceptions;

    /**
     * 定义map的builder对象，去构建ImmutableMap
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();


    @ExceptionHandler(CustomException.class)
    public ResponseResult customException(CustomException customException){
        lg.error("System catch customException:{}", customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception exception){
        lg.error("System catch exception:{}",exception.getMessage());
        if (exceptions == null) {
            exceptions = builder.build();
        }

        ResultCode resultCode = exceptions.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        }else{
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }


    }

    static {
        // 定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
}
