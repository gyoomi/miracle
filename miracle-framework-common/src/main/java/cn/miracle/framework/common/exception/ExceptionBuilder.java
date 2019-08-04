/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.common.exception;


import cn.miracle.framework.common.model.response.ResultCode;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/6/21 11:26
 */
public class ExceptionBuilder {

    public static void build(ResultCode resultCode) {
        throw new CustomException(resultCode);
    }

}
