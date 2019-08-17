/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.framework.model.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/16 11:43
 */
@Data
@Entity
@Table(name = "orderDetail")
public class OrderDetail {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 商品名
     */
    private String name;

    /**
     * 数量
     */
    private Integer num;

}
