package cn.miracle.service.manage.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 22:16
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"cn.miracle.service.manage.auth"})
@ComponentScan(basePackages = {"cn.miracle.framework"})
@EnableFeignClients
public class ServiceManageAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceManageAuthApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
