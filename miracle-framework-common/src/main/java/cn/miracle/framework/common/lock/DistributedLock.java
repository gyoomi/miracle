/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.common.lock;

/**
 * 接口功能描述
 *
 * @author Leon
 * @version 2019/8/9 16:23
 */
public interface DistributedLock {

    /**
     * Get a lock
     *
     * @return
     */
    boolean lock();

    /**
     * Release a existed lock
     *
     */
    void unlock();
}
