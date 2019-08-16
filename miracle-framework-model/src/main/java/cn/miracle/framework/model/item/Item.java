/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.model.item;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/16 10:15
 */
@Data
@Entity
@Table(name = "item")
public class Item implements Serializable {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 商品类型
     */
    private String itemType;

    /**
     * 商品编号
     */
    private String itemNo;

    /**
     * 价格
     */
    private String price;

    /**
     * 描述
     */
    private String description;

    /**
     * 所属商户id
     */
    private String businessId;

    /**
     * 描述
     */
    private String remark;
}
