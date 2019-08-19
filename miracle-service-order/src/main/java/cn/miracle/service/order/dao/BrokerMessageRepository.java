/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.order.dao;

import cn.miracle.framework.model.order.BrokerMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BrokerMessageRepository
 *
 * @author Leon
 * @version 2019/8/19 14:18
 */
public interface BrokerMessageRepository extends JpaRepository<BrokerMessage, Long> {
}
