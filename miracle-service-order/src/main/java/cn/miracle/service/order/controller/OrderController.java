package cn.miracle.service.order.controller;

import cn.miracle.framework.common.util.IdWorker;
import cn.miracle.framework.model.order.BrokerMessage;
import cn.miracle.framework.model.order.OrderInfo;
import cn.miracle.service.order.constant.Constants;
import cn.miracle.service.order.dao.BrokerMessageRepository;
import cn.miracle.service.order.dao.OrderInfoRepository;
import cn.miracle.service.order.mq.producer.OrderSender;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    private OrderSender orderSender;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private BrokerMessageRepository brokerMessageRepository;

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
        OrderInfo order = new OrderInfo();
        order.setId(idWorker.nextId());
        order.setOrderNo(num++);
        order.setContact(name);
        order.setOrderType("衣服");
        order.setTotal(10.90);
        order.setOrderStatus(10);

        BrokerMessage brokerMessage = new BrokerMessage();
        brokerMessage.setId(idWorker.nextId());
        brokerMessage.setCreateDate(new Date());
        brokerMessage.setIsDelayed(1);
        brokerMessage.setDelayTime(time);
        brokerMessage.setContent(JSON.toJSONString(order));
        brokerMessage.setExchangeName("order_delay_exchange");
        brokerMessage.setRoutingKey("order_delay_key");
        brokerMessage.setStatus(Constants.ORDER_SENDING);
        orderInfoRepository.save(order);
        brokerMessageRepository.save(brokerMessage);
        // send message
        orderSender.sendMessage(order, brokerMessage);
        return "下单成功！";
    }
}
