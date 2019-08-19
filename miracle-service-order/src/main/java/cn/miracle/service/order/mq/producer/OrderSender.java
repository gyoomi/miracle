/**
 * Copyright © 2019, LeonKeh
 * <p>
 * All Rights Reserved.
 */

package cn.miracle.service.order.mq.producer;

import cn.miracle.framework.model.order.BrokerMessage;
import cn.miracle.framework.model.order.OrderInfo;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/19 10:50
 */
@Component
public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderInfo order, BrokerMessage brokerMessage) {
        // Unique message id
        CorrelationData correlationData = new CorrelationData(brokerMessage.getId().toString());
        MessagePostProcessor mpp = message -> {
            if (brokerMessage.getIsDelayed() == 1) {
                message.getMessageProperties().setDelay(brokerMessage.getDelayTime());
            }
            return message;
        };
        rabbitTemplate.convertAndSend(brokerMessage.getExchangeName(), brokerMessage.getRoutingKey(), order, mpp, correlationData);
    }
}