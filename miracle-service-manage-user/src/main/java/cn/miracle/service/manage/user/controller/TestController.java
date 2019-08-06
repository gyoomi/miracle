package cn.miracle.service.manage.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 19:32
 */
@RestController
@RequestMapping(value = "/manage/user/test")
public class TestController {

    @GetMapping(value = "/test1")
    public Map test1() {
        return new HashMap(){{
            put("name", "张三");
            put("age", 22);
        }};
    }

    @GetMapping(value = "/test2")
    public Map test2() {
        return new HashMap(){{
            put("name", "张三22222222");
            put("age", 2333333);
        }};
    }
}
