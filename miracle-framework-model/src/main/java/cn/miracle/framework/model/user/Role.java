package cn.miracle.framework.model.user;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 *
 * @author Leon
 * @version 2018/11/14 16:51
 */
@Data
@Entity
@Table(name = "role")
public class Role {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 角色标识，唯一
     */
    @Column
    private String code;

    /**
     * 角色名称
     */
    @Column
    private String name;

    /**
     * 角色级别
     */
    @Column
    private Integer level;

    /**
     * 序号
     */
    @Column
    private Integer num;

}
