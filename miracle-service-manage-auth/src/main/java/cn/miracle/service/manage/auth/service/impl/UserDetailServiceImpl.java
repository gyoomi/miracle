package cn.miracle.service.manage.auth.service.impl;

import cn.miracle.service.manage.auth.service.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 18:37
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                return new User(username, clientDetails.getClientSecret(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }

        if (username == null) {
            return null;
        }
        cn.miracle.framework.model.user.User curUser = new cn.miracle.framework.model.user.User();
        curUser.setId(11111L);
        curUser.setLoginName("admin");
        curUser.setPassword(passwordEncoder.encode("admin"));
        if (curUser == null) {
            return null;
        }
        // TODO
        List<String> permissionList = new ArrayList<>();
        String curUserPassword = curUser.getPassword();
        // TODO
        JwtUser jwtUser = new JwtUser(username, curUserPassword, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
        // Avatar
        jwtUser.setAvatar(curUser.getAvatar());
        // Id
        jwtUser.setId(curUser.getId());
        return jwtUser;
    }

}
