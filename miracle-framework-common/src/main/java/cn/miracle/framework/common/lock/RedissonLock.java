package cn.miracle.framework.common.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/9 23:17
 */
public class RedissonLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLock.class);

    private static DistributedLock redissonLock;

    public static void setLocker(DistributedLock locker) {
        redissonLock = locker;
    }

    /**
     * (non-Javadoc)
     *
     * @see DistributedLock#lock(String) 
     */
    public static void lock(String key) {
        redissonLock.lock(key);
        LOGGER.info("分布式锁获取成功：{}", key);
    }

    /**
     * (non-Javadoc)
     *
     * @see DistributedLock#unlock(String) 
     */
    public static void unlock(String key) {
        LOGGER.info("释放分布式锁：{}", key);
        redissonLock.unlock(key);
    }

    /**
     * (non-Javadoc)
     *
     * @see DistributedLock#lock(String, int) 
     */
    public static void lock(String key, int timeout) {
        redissonLock.lock(key, timeout);
        LOGGER.info("分布式锁获取成功：{}", key);
    }

    /**
     * (non-Javadoc)
     *
     * @see DistributedLock#lock(String, TimeUnit, int)
     */
    public static void lock(String key, TimeUnit unit , int timeout) {
        redissonLock.lock(key, unit, timeout);
        LOGGER.info("分布式锁获取成功：{}", key);
    }
}
