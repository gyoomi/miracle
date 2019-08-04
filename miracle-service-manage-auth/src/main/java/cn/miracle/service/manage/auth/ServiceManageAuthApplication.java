package cn.miracle.service.manage.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 22:16
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceManageAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManageAuthApplication.class, args);
    }
}
