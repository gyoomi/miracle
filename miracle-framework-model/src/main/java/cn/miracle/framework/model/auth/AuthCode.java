package cn.miracle.framework.model.auth;

import cn.miracle.framework.common.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:41
 */
public enum AuthCode implements ResultCode {

    /**
     * 申请令牌失败
     */
    AUTH_APPLY_TOKEN_ERROR(false,23001,"申请令牌失败！"),

    /**
     * 保存令牌失败
     */
    AUTH_SAVE_TOKEN_ERROR(false,23002,"保存令牌失败！"),

    /**
     * 账户不存在
     */
    AUTH_ACCOUNT_NOT_EXISTS(false,23003,"账户不存在！"),

    /**
     * 认证失败
     */
    AUTH_CREDENTIAL_ERROR(false,23004,"认证失败！");

    private boolean success;

    private int code;

    private String message;

    private AuthCode(boolean success, int code, String message){
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

    private static final ImmutableMap<Integer, AuthCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, AuthCode> builder = ImmutableMap.builder();
        for (AuthCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }
}
