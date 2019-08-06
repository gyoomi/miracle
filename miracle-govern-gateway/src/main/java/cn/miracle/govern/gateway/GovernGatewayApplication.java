package cn.miracle.govern.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 23:08
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GovernGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovernGatewayApplication.class, args);
    }
}
