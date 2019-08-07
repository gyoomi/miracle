package cn.miracle.service.manage.auth.service.impl;

import cn.miracle.framework.model.user.Menu;
import cn.miracle.framework.model.user.ext.UserExt;
import cn.miracle.service.manage.auth.service.JwtUser;
import cn.miracle.service.manage.auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
@Service("UserDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

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
        UserExt userExt = userService.findUserExtByLoginName(username);
        if (userExt == null) {
            return null;
        }
        List<String> permissionList = new ArrayList<>();
        List<Menu> permissionListToUse = userExt.getPermissionList();
        // 处理权限标识
        permissionListToUse.stream().map(Menu::getCode).forEach(permissionList::add);
        String permissionString = StringUtils.join(permissionList, ",");

        String curUserPassword = userExt.getPassword();
        JwtUser jwtUser = new JwtUser(username, curUserPassword, AuthorityUtils.commaSeparatedStringToAuthorityList(permissionString));
        jwtUser.setAvatar(userExt.getAvatar());
        jwtUser.setId(userExt.getId());
        return jwtUser;
    }

}
