package cn.miracle.service.manage.auth.controller;

import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.common.model.response.CommonCode;
import cn.miracle.framework.common.model.response.ResponseResult;
import cn.miracle.framework.model.auth.AuthToken;
import cn.miracle.framework.model.user.User;
import cn.miracle.service.manage.auth.feign.TestFeignClient;
import cn.miracle.service.manage.auth.properties.AuthProperties;
import cn.miracle.service.manage.auth.service.AuthService;
import cn.miracle.service.manage.auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:19
 */
@RestController
public class AuthController {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthService authService;

    @Autowired
    private TestFeignClient testFeignClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public AuthToken login(String loginName, String password) {
        if (StringUtils.isAnyBlank(loginName, password)) {
            ExceptionBuilder.build(CommonCode.INVALID_PARAM);
        }
        AuthToken token = authService.login(loginName, password, authProperties.getClientId(), authProperties.getClientSecret());
        return token;
    }

    /**
     * Register a new user
     *
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseResult register(String loginName, String password) {
        if (StringUtils.isAnyBlank(loginName, password)) {
            ExceptionBuilder.build(CommonCode.INVALID_PARAM);
        }
        authService.register(new User().setLoginName(loginName).setPassword(password));
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * Logout
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/logout")
    public ResponseResult logout(HttpServletRequest request) {
        String jwtToken = getJwtFromHeader(request);
        if (jwtToken == null) {
            ExceptionBuilder.build(CommonCode.FAIL);
        }
        String key = "user_token:" + jwtToken;
        stringRedisTemplate.delete(key);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @GetMapping("/test1")
    public Object test1() {
        return new HashMap(){{
           put("name", "authname");
           put("age", 22);
        }};
    }

    @GetMapping("/test2")
    public Map test2() {
        return testFeignClient.test2();
    }

    private String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        if (!StringUtils.startsWith(authorization, "Bearer ")) {
            return null;
        }
        return StringUtils.substringAfter(authorization, "Bearer ");
    }
}
