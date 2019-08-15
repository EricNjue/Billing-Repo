package com.realestatekenya.billingmicroservicereadside.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@Data
public class ApplicationConfigReader {

    @Value("${billing-microservice.exchange.name}")
    private String billingMicroServiceExchange;

    @Value("${billing-microservice.queue.name}")
    private String billingMicroServiceQueue;

    @Value("${billing-microservice.routing.key}")
    private String billingMicroServiceRoutingKey;
}
