/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.common.flowlimit.redisson;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/27 15:44
 */
public enum LimiterStrategy {

    /**
     * general
     */
    General,

    /**
     * IP
     */
    IP,

    /**
     * User
     */
    User;

    LimiterStrategy() {}
}
