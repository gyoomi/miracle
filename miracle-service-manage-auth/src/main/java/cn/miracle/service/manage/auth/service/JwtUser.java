package cn.miracle.service.manage.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/4 18:34
 */
public class JwtUser extends User {

    /**
     * id
     */
    private String id;

    /**
     * 登录名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户类型
     */
    private String type;

    /**
     * 所属公司id
     */
    private String companyId;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
