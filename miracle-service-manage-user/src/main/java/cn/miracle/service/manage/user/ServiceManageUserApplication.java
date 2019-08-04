package cn.miracle.service.manage.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 21:32
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(value = "cn.miracle.framework.model.user")
@ComponentScan(basePackages= {"cn.miracle.framework"})
@ComponentScan(basePackages = {"cn.miracle.service.manage.user"})
public class ServiceManageUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManageUserApplication.class, args);
    }
}
