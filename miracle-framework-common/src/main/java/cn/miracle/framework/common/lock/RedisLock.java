/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.common.lock;

import cn.miracle.framework.common.util.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/9 16:34
 */
public class RedisLock implements DistributedLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);

    private static StringRedisTemplate stringRedisTemplate;

    private String lockKey;

    private int autoUnlockTime = 5 * 1000;

    private int waitLockTime = 5 * 1000;

    private boolean locked = false;

    public RedisLock(String lockKey) {
        this.lockKey = lockKey;
    }

    public RedisLock(String lockKey, int autoUnlockTime) {
        this.lockKey = lockKey;
        this.autoUnlockTime = autoUnlockTime;
    }

    public RedisLock(String lockKey, int autoUnlockTime, int waitLockTime) {
        this.lockKey = lockKey;
        this.autoUnlockTime = autoUnlockTime;
        this.waitLockTime = waitLockTime;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean lock() {
        int waitTime = this.waitLockTime;
        if (stringRedisTemplate == null) {
            stringRedisTemplate = ApplicationContextUtils.getBean(StringRedisTemplate.class);
        }
        while (waitTime >= 0) {
            stringRedisTemplate.boundValueOps(lockKey).set("lock", autoUnlockTime, TimeUnit.MILLISECONDS);

        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void unlock() {

    }
}
