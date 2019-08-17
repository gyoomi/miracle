package cn.miracle.service.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/8/17 15:17
 */
@Configuration
public class RabbitmqConfig {

    /**
     * the exchange of delay
     *
     * @return
     */
    @Bean
    public CustomExchange delayExchange() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        // type must be 'x-delayed-message'
        // CustomExchange is must
        return new CustomExchange("delay_exchange", "x-delayed-message", true, false, args);
    }

    /**
     * the queue of delay
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        return new Queue("delay_queue", true);
    }

    /**
     * Binding the queue to the exchange
     *
     * @return
     */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay_key").noargs();
    }
}
