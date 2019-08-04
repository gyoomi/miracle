package cn.miracle.framework.model.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author Leon
 * @version 2018/11/14 16:51
 */
@Data
@Entity
@Table(name = "menu")
public class Menu {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 菜单标识，唯一
     */
    @Column
    private String code;

    /**
     * 菜单名称
     */
    @Column
    private String name;

    /**
     * url
     */
    @Column
    private String url;

    /**
     * 类型：菜单或按钮 M/B
     */
    @Column
    private String type;

    /**
     * 上级id
     */
    @Column
    private String parentId;

    /**
     * 排序号
     */
    @Column
    private Integer num;

    /**
     * 前台路由路径
     */
    @Column
    private String path;

    /**
     * 菜单图标
     */
    @Column
    private String icon;

    /**
     * 菜单的所有路径（逗号分隔）
     */
    @Column(length = 1000)
    private String allPath;

    /**
     * 子节点集合
     */
    @Transient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children = new ArrayList<>();

}
