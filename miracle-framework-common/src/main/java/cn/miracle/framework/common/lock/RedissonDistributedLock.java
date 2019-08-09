package cn.miracle.framework.common.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/9 23:06
 */
public class RedissonDistributedLock implements DistributedLock {

    private RedissonClient redissonClient;

    /**
     * (non-Javadoc)
     * @see DistributedLock#lock(String)
     */
    @Override
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.lock();
    }

    /**
     * (non-Javadoc)
     * @see DistributedLock#unlock(String) 
     */
    @Override
    public void unlock(String key) {
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }

    /**
     * (non-Javadoc)
     * @see DistributedLock#lock(String, int) 
     */
    @Override
    public void lock(String key, int timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * (non-Javadoc)
     * @see DistributedLock#lock(String, TimeUnit, int)
     */
    @Override
    public void lock(String key, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(key);
        lock.lock(timeout, unit);
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
