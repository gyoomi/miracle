package cn.miracle.service.manage.user.config;

import cn.miracle.framework.common.util.IdWorkProperties;
import cn.miracle.framework.common.util.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 1:02
 */
@Configuration
@EnableConfigurationProperties(IdWorkProperties.class)
public class IdWorkConfig {

    @Bean
    public IdWorker idWorker(IdWorkProperties prop) {
        return new IdWorker(prop.getWorkerId(), prop.getDataCenterId());
    }
}
