/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.common.lock;

import java.util.concurrent.TimeUnit;

/**
 * 接口功能描述
 *
 * @author Leon
 * @version 2019/8/9 16:23
 */
public interface DistributedLock {


    /**
     * Get a distributed lock for {@code key}
     *
     * @param key
     */
    void lock(String key);

    /**
     * Release a distributed lock for {@code key}
     *
     * @param key
     */
    void unlock(String key);

    /**
     * Get a distributed lock for {@code key} within {@code timeout} <br/>
     * Default time unit is <tt>milliseconds</tt>
     *
     * @param lockKey
     * @param timeout  must not be {@literal null}
     */
    void lock(String lockKey, int timeout);

    /**
     * Get a distributed lock for {@code key} within {@code timeout} at {@code unit}
     *
     * @param lockKey
     * @param unit    must not be {@literal null}
     * @param timeout must not be {@literal null}
     */
    void lock(String lockKey, TimeUnit unit , int timeout);
}
