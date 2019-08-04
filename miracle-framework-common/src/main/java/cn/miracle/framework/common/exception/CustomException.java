package cn.miracle.framework.common.exception;


import cn.miracle.framework.common.model.response.ResultCode;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/6/17 0:16
 */
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return resultCode;
    }
}
