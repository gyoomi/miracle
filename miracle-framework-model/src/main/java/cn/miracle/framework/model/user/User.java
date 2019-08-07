package cn.miracle.framework.model.user;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 用户
 *
 * @author Leon
 * @version 2018/11/14 16:51
 */
@Data
@Entity
@Table(name = "user")
@Accessors(chain = true)
public class User {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 登录名
     */
    @Column
    private String loginName;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真名
     */
    @Column
    private String realName;

    /**
     * 手机号
     */
    @Column
    private String phone;

    /**
     * 头像、照片
     */
    @Column
    private String avatar;


}
