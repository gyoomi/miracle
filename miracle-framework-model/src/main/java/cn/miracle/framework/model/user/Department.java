package cn.miracle.framework.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 部门
 *
 * @author Leon
 * @version 2018/11/14 16:51
 */
@Data
@Entity
@Table(name = "department")
public class Department {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 部门名称
     */
    @Column
    private String name;

    /**
     * 部门编码（唯一）
     */
    @Column
    private String code;

    /**
     * 父部门节点（没有父节点值为0）
     */
    @Column
    private String parentId;

    /**
     * 排序号
     */
    @Column
    private Integer num;

    /**
     * 级别
     */
    @Column
    private Integer level;

    /**
     * 联系人
     */
    @Column
    private String contact;

    /**
     * 联系电话
     */
    @Column
    private String phone;

    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * 下一级节点集合
     */
    @Transient
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Department> children;
}
