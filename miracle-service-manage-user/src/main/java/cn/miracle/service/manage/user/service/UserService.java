package cn.miracle.service.manage.user.service;

import cn.miracle.framework.model.user.User;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 21:55
 */
public interface UserService {

    /**
     * 保存
     *
     * @param user
     * @return
     */
    User save(User user);

    /**
     * 更新
     *
     * @param user
     * @return
     */
    User update(User user);

    /**
     * 更新用户
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

}
