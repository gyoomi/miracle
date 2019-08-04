package cn.miracle.framework.common.model.response;

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
@ToString
public enum CommonCode implements ResultCode {

    /**
     * 非法参数
     */
    INVALID_PARAM(false,10003,"非法参数！"),

    /**
     * 操作成功
     */
    SUCCESS(true,10000,"操作成功！"),

    /**
     * 操作失败
     */
    FAIL(false,11111,"操作失败！"),

    /**
     * 此操作需要登陆系统
     */
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),

    /**
     * 权限不足，无权操作
     */
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),

    /**
     * 抱歉，系统繁忙，请稍后重试
     */
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！");

    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 操作代码
     */
    private int code;

    /**
     * 提示信息
     */
    private String message;


    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
