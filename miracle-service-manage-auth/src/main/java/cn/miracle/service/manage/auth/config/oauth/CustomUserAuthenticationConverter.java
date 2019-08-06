/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.manage.auth.config.oauth;

import cn.miracle.service.manage.auth.service.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 11:06
 */
@Component
public class CustomUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());

        Object principal = authentication.getPrincipal();
        JwtUser jwtUser = null;
        if (principal instanceof JwtUser) {
            jwtUser = (JwtUser) principal;
        } else {
            jwtUser = (JwtUser) userDetailsService.loadUserByUsername(authentication.getName());
        }
        response.put("id", jwtUser.getId());
        response.put("avatar", jwtUser.getAvatar());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }
}
