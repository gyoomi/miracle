/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.order.dao;

import cn.miracle.framework.model.order.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * OrderInfoRepository
 *
 * @author Leon
 * @version 2019/8/19 14:19
 */
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
}
