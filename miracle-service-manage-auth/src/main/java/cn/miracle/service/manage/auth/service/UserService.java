package cn.miracle.service.manage.auth.service;

import cn.miracle.framework.model.user.ext.UserExt;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/7 21:25
 */
public interface UserService {

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     */
    UserExt findUserExtByLoginName(String loginName);
}
