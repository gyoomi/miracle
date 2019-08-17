package cn.miracle.service.order.mq;

import cn.miracle.framework.model.order.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/17 15:55
 */
@Component
public class OrderListener {

    @RabbitListener(queues = "delay_queue")
    public void listenOrderCancel(Channel channel, Message message, Order order) {
        if (Objects.equals(10, order.getOrderStatus())) {
            // 设置订单为超时过期，并存入数据库
            System.out.println(LocalDateTime.now() + "  订单编号: " + order.getOrderNo() + " 已超时支付，已自动取消！");
        }
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
