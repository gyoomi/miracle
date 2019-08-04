package cn.miracle.framework.common.model.response;

/**
 * 类功能描述
 *   10000-- 通用错误代码
 *   22000-- 媒资错误代码
 *   23000-- 用户中心错误代码
 *
 * @author Leon
 * @version 2019/6/21 11:26
 */
public interface ResultCode {

    /**
     * 操作是否成功,true为成功，false操作失败
     *
     * @return
     */
    boolean success();

    /**
     * 操作代码
     *
     * @return
     */
    int code();

    /**
     * 提示信息
     *
     * @return
     */
    String message();

}
