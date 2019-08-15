package com.realestatekenya.blueprintcommonmicroservice.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSender {

    // This is best used when sending to a Topic Exchange i.e the Routing Key is Important and will not be ignored ...
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue");
    }

    // Here the routing key will be ignored. Ideal for publisher subscriber model ...
    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, Object data) {
        log.info("FANOUT EXCHANGE: Sending message to the queue. Message= {}", data);
        rabbitTemplate.convertAndSend(exchange, "", data);
        log.info("Fanout Exchange: Message Sent...");
    }
}
