package cn.miracle.framework.model.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/3 21:46
 */
@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    @Column
    private Long userId;

    /**
     * 角色id
     */
    @Column
    private Long roleId;


}
