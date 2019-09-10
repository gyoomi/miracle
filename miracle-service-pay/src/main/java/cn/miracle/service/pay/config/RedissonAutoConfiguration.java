package cn.miracle.service.pay.config;

import cn.miracle.framework.common.lock.DistributedLock;
import cn.miracle.framework.common.lock.RedissonDistributedLock;
import cn.miracle.framework.common.lock.RedissonLock;
import cn.miracle.framework.common.lock.RedissonProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/9 23:24
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {

    @Autowired
    private RedissonProperties redssionProperties;

    @Bean
    @ConditionalOnProperty(value = "redisson.address")
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if(redssionProperties.getPassword() != null && !"".equals(redssionProperties.getPassword())) {
            singleServerConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnProperty(value = "redisson.master-name")
    public RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                .addSentinelAddress(redssionProperties.getSentinelAddresses())
                .setMasterName(redssionProperties.getMasterName())
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
        if(redssionProperties.getPassword() != null && !"".equals(redssionProperties.getPassword())) {
            sentinelServersConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnProperty(value = "redisson.client-name")
    public RedissonClient redissonCluster() {
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .setClientName(redssionProperties.getClientName())
                .addNodeAddress(redssionProperties.getNodeAddresses().split(","))
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
        if(redssionProperties.getPassword() != null && !"".equals(redssionProperties.getPassword())) {
            clusterServersConfig.setPassword(redssionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    @Bean
    public DistributedLock distributedLock(RedissonClient redissonClient) {
        RedissonDistributedLock redissonDistributedLock = new RedissonDistributedLock();
        redissonDistributedLock.setRedissonClient(redissonClient);
        RedissonLock.setLocker(redissonDistributedLock);
        return redissonDistributedLock;
    }

}
