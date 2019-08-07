package cn.miracle.service.manage.auth.service;

import cn.miracle.framework.model.auth.AuthToken;
import cn.miracle.framework.model.user.User;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/6 21:21
 */
public interface AuthService {


    /**
     * do login
     *
     * @param loginName
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    AuthToken login(String loginName, String password, String clientId, String clientSecret);

    /**
     * Register a new user
     *
     * @param user
     * @return
     */
    User register(User user);
}
