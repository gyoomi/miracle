package cn.miracle.service.order.controller;

import cn.miracle.framework.common.util.IdWorker;
import cn.miracle.framework.model.order.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/17 16:01
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private long num = 1;

    /**
     * test for delay queue
     *
     * @param time
     * @param name
     * @return
     */
    @GetMapping(value = "/save/{time}/{name}")
    public String save(@PathVariable Integer time, @PathVariable String name) {
        Order order = new Order();
        order.setId(idWorker.nextId());
        order.setOrderNo(num++);
        order.setContact(name);
        order.setOrderType("衣服");
        order.setTotal(10.90);
        order.setOrderStatus(10);
        // send to the mq
        rabbitTemplate.convertAndSend("delay_exchange", "delay_key", order, message -> {
            message.getMessageProperties().setDelay(time);
            return message;
        });
        System.out.println("发送下单: " + LocalDateTime.now());
        return "下单成功！";
    }
}
