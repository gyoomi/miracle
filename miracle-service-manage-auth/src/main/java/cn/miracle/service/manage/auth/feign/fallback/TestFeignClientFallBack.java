/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.manage.auth.feign.fallback;

import cn.miracle.service.manage.auth.feign.TestFeignClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/7 9:49
 */
@Component
public class TestFeignClientFallBack implements TestFeignClient {


    @Override
    public Map test1() {
        return new HashMap() {{
            put("code", 500);
            put("msg", "error");
            put("test1111", "11111");
        }};
    }

    @Override
    public Map test2() {
        return new HashMap() {{
            put("code", 500);
            put("msg", "error");
            put("test222", "222222");
        }};
    }
}
