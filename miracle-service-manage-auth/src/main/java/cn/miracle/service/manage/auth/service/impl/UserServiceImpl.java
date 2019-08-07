package cn.miracle.service.manage.auth.service.impl;

import cn.miracle.framework.model.user.Menu;
import cn.miracle.framework.model.user.ext.UserExt;
import cn.miracle.service.manage.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/7 21:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * {@inheritDoc}
     *
     * @param loginName
     * @return
     */
    @Override
    public UserExt findUserExtByLoginName(String loginName) {
        String sql = " SELECT * FROM user WHERE login_name = ? ";
        List<UserExt> userExtList = jdbc.query(sql, new BeanPropertyRowMapper<>(UserExt.class), loginName);
        if (userExtList.size() < 1) {
            return null;
        }
        UserExt userExt = userExtList.get(0);

        String permissionSql = " SELECT "
                             + "     t1.* "
                             + " FROM menu t1 "
                             + " LEFT JOIN role_menu t2 ON t2.menu_id = t1.id "
                             + " LEFT JOIN user_role t3 ON t3.role_id = t2.role_id "
                             + " WHERE t3.user_id = ? ";
        List<Menu> menuList = jdbc.query(permissionSql, new BeanPropertyRowMapper<>(Menu.class), userExt.getId());
        userExt.setPermissionList(menuList);
        return userExt;
    }
}
