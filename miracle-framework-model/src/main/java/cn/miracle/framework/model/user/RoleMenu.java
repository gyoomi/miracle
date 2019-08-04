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
 * @version 2019/8/3 21:47
 */
@Data
@Entity
@Table(name = "role_menu")
public class RoleMenu {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 角色id
     */
    @Column
    private Long roleId;

    /**
     * 菜单id
     */
    @Column
    private Long menuId;
}
