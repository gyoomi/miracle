package cn.miracle.framework.model.user.ext;

import cn.miracle.framework.model.user.Menu;
import lombok.Data;

import java.util.List;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/7 21:17
 */
@Data
public class UserExt {

    /**
     * id
     */
    private Long id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像、照片
     */
    private String avatar;

    /**
     * 权限列表
     */
    private List<Menu> permissionList;
}
