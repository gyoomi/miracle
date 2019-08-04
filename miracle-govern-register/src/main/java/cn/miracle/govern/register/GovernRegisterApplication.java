package cn.miracle.govern.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Register Service
 *
 * @author Leon
 * @version 2019/8/3 17:24
 */
@SpringBootApplication
@EnableEurekaServer
public class GovernRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovernRegisterApplication.class, args);
    }
}
