package com.realestatekenya.billingmicroservicewriteside;

import com.realestatekenya.billingmicroservicewriteside.config.ApplicationConfigReader;
import com.realestatekenya.blueprintcommonmicroservice.amqp.MessageSender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;


@SpringBootApplication
@EnableRabbit
public class BillingMicroserviceWritesideApplication implements RabbitListenerConfigurer {

    @Autowired
    private ApplicationConfigReader applicationConfig;

    public ApplicationConfigReader getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfigReader applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(BillingMicroserviceWritesideApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BillingMicroserviceWritesideApplication.class);
    }

    /* This bean is to read the properties file configs */
    @Bean
    public ApplicationConfigReader applicationConfig() {
        return new ApplicationConfigReader();
    }

    /* Creating a bean for the message queue Exchange */
//    @Bean
//    public TopicExchange getBillingMicroServiceExchange() {
//        return new TopicExchange(getApplicationConfig().getBillingMicroServiceExchange());
//    }


    @Bean
    public FanoutExchange getBillingMicroServiceExchange() {
        return new FanoutExchange(getApplicationConfig().getBillingMicroServiceExchange());
    }

    /* Creating a bean for the Message queue */
    @Bean
    public Queue getBillingMicroServiceQueue() {
        return new Queue(getApplicationConfig().getBillingMicroServiceQueue(), true, false, false);
    }


    /* Binding between Exchange and Queue using Routing Key */
    @Bean
    public Binding getBillingMicroServiceBinding() {
        return BindingBuilder.bind(getBillingMicroServiceQueue())
                .to(getBillingMicroServiceExchange());
    }


    /* Bean for rabbit template */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public MessageSender getMessageSender() {
        return new MessageSender();
    }
}
