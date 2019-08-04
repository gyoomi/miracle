package cn.miracle.framework.common.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 类功能描述
 *   10000-- 通用错误代码
 *   22000-- 媒资错误代码
 *   23000-- 用户中心错误代码
 *
 * @author Leon
 * @version 2019/6/21 11:26
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {

    /**
     * 操作是否成功
     */
    private boolean success = SUCCESS;

    /**
     * 操作代码
     */
    private int code = SUCCESS_CODE;

    /**
     * 提示信息
     */
    private String message;

    public ResponseResult(ResultCode resultCode){
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }

}
