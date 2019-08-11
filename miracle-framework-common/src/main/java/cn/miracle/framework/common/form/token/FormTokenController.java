package cn.miracle.framework.common.form.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/11 23:10
 */
@RestController
public class FormTokenController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Common for get valid token
     *
     * @return
     */
    @GetMapping(value = "/form/token")
    public Map<String, String> formToken() {
        String formTokenUUID = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(formTokenUUID, "1", 10, TimeUnit.MINUTES);
        return new HashMap<String, String>(1) {{
            put("formToken", formTokenUUID);
        }};
    }
}
