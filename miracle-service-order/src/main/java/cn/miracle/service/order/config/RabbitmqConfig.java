package cn.miracle.service.order.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
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

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(rabbitProperties.getAddresses());
        factory.setUsername(rabbitProperties.getUsername());
        factory.setPassword(rabbitProperties.getPassword());
        factory.setPublisherConfirms(true);
        factory.setPublisherReturns(true);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        // 必须设置为 true，不然当 发送到交换器成功，但是没有匹配的队列，不会触发 ReturnCallback 回调
        // 而且 ReturnCallback 比 ConfirmCallback 先回调，意思就是 ReturnCallback 执行完了才会执行 ConfirmCallback
        template.setMandatory(rabbitProperties.getTemplate().getMandatory());
        template.setConfirmCallback(new ConfirmCallbackListener());
        template.setReturnCallback(new ReturnCallbackListener());
        return template;
    }


    public static class ConfirmCallbackListener implements RabbitTemplate.ConfirmCallback {

        private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmCallbackListener.class);

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            LOGGER.info("----------------start-------------------");
            LOGGER.info("CorrelationData -> {}", correlationData);
            LOGGER.info("ack -> {}", ack);
            LOGGER.info("cause -> {}", cause);
            LOGGER.info("----------------end-------------------");
        }
    }

    public static class ReturnCallbackListener implements RabbitTemplate.ReturnCallback {

        private static final Logger LOGGER = LoggerFactory.getLogger(ReturnCallbackListener.class);

        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            LOGGER.info("----------------start-------------------");
            LOGGER.info("Message -> {}", message);
            LOGGER.info("replyCode -> {}", replyCode);
            LOGGER.info("replyText -> {}", replyText);
            LOGGER.info("exchange -> {}", exchange);
            LOGGER.info("routingKey -> {}", routingKey);
            LOGGER.info("----------------end-------------------");
        }
    }

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
