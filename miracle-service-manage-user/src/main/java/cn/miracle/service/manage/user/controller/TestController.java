package cn.miracle.service.manage.user.controller;

import cn.miracle.framework.common.form.token.FormToken;
import cn.miracle.framework.common.lock.RedissonLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 19:32
 */
@RestController
@RequestMapping(value = "/manage/user/test")
public class TestController {

    private String lockKey = "test_lock_key";

    @GetMapping(value = "/test1")
    public Map test1() {
        return new HashMap(){{
            put("name", "张三");
            put("age", 22);
        }};
    }

    @GetMapping(value = "/test2")
    public void test2(String name) throws Exception {
        new Thread(() -> {
            RedissonLock.lock(lockKey, TimeUnit.MINUTES, 1);
            System.out.println(LocalDateTime.now() + " " + name + "start......");
            for (int i = 1; i < 11; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println(LocalDateTime.now().toLocalTime() +" " + name +" " +i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(LocalDateTime.now() + " " + name + "end......");
            RedissonLock.unlock(lockKey);
        }).start();
        System.out.println("main do over");
    }

    @GetMapping(value = "/save")
    @FormToken
    public String save(String test) {
        System.out.println(LocalDateTime.now().toLocalDate());
        System.out.println(LocalDateTime.now().toLocalTime());
        System.out.println(LocalDateTime.now());
        return "ok";
    }
}
