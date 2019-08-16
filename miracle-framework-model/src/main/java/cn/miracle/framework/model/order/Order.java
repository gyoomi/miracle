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
@Table(name = "order")
public class Order {


    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 订单编号
     */
    private Long orderNo;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 订单金额
     */
    private Double total;

    /**
     * 订单状态
     *   10 - 待支付；
     *   15 - 支付中；
     *   20 - 已支付；
     *   30 - 已取消；
     *   40 - 超时取消；
     *   50 - 已退款；
     *   60 - 已完成
     */
    private Integer orderStatus;

}
