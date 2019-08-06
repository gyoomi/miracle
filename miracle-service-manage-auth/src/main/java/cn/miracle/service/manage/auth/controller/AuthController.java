package cn.miracle.service.manage.auth.controller;

import cn.miracle.framework.common.exception.ExceptionBuilder;
import cn.miracle.framework.common.model.response.CommonCode;
import cn.miracle.framework.model.auth.AuthToken;
import cn.miracle.service.manage.auth.properties.AuthProperties;
import cn.miracle.service.manage.auth.service.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/test")
    public Map test() {
        return new HashMap(){{
            put("name", "沾伞");
            put("age", 22);
        }};
    }
}
