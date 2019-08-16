/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/16 11:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(value = "cn.miracle.framework.model.pay")
@ComponentScan(basePackages= {"cn.miracle.framework"})
@ComponentScan(basePackages = {"cn.miracle.service.pay"})
public class ServicePayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicePayApplication.class, args);
    }
}
