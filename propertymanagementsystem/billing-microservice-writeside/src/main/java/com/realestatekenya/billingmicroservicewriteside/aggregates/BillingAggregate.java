package com.realestatekenya.billingmicroservicewriteside.aggregates;

import com.realestatekenya.billingmicroservicewriteside.commands.CreateInvoiceCommand;
import com.realestatekenya.billingmicroservicewriteside.commands.UpdateInvoiceCommand;
import com.realestatekenya.billingmicroservicewriteside.config.ApplicationConfigReader;
import com.realestatekenya.billingmicroservicewriteside.dtos.InvoiceItemDTO;
import com.realestatekenya.billingmicroservicewriteside.events.InvoiceCreatedEvent;
import com.realestatekenya.billingmicroservicewriteside.events.InvoiceUpdatedEvent;
import com.realestatekenya.billingmicroservicewriteside.utils.InvoiceStatus;
import com.realestatekenya.blueprintcommonmicroservice.amqp.MessageSender;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.List;

@Aggregate
@Slf4j
@Data
public class BillingAggregate {

    @AggregateIdentifier
    private String id;
    private String tenantId;
    private String propertyId;
    private String status;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private List<InvoiceItemDTO> invoiceItems;

    public BillingAggregate() {
    }


    @CommandHandler
    public BillingAggregate(CreateInvoiceCommand createInvoiceCommand) {
        AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.id, createInvoiceCommand.tenantId,
                createInvoiceCommand.propertyId, LocalDate.now(), createInvoiceCommand.dueDate,
                createInvoiceCommand.invoiceItems));
    }

    @EventSourcingHandler
    protected void handle(InvoiceCreatedEvent invoiceCreatedEvent, RabbitTemplate rabbitTemplate,
                          @Qualifier("applicationConfig") ApplicationConfigReader applicationConfig,
                          @Qualifier("getMessageSender") MessageSender messageSender) {

        log.info("Handling InvoiceCreatedEvent...");

        this.id = invoiceCreatedEvent.id;
        this.tenantId = invoiceCreatedEvent.tenantId;
        this.propertyId = invoiceCreatedEvent.propertyId;
        this.status = String.valueOf(InvoiceStatus.CREATED);
        this.creationDate = invoiceCreatedEvent.creationDate;
        this.dueDate = invoiceCreatedEvent.dueDate;
        this.invoiceItems = invoiceCreatedEvent.invoiceItems;

        String exchange = applicationConfig.getBillingMicroServiceExchange();
        String routingKey = applicationConfig.getBillingMicroServiceRoutingKey();

        messageSender.sendMessage(rabbitTemplate, exchange, invoiceCreatedEvent);

        log.info("InvoiceCreatedEvent published to the queue...");
    }

    @CommandHandler
    protected void handle(UpdateInvoiceCommand updateInvoiceCommand) {
        AggregateLifecycle.apply(new InvoiceUpdatedEvent(updateInvoiceCommand.id, updateInvoiceCommand.tenantId,
                updateInvoiceCommand.propertyId, updateInvoiceCommand.dueDate, updateInvoiceCommand.invoiceItems));
    }


    @EventSourcingHandler
    protected void handle(InvoiceUpdatedEvent invoiceUpdatedEvent, RabbitTemplate rabbitTemplate,
                          @Qualifier("applicationConfig") ApplicationConfigReader applicationConfig,
                          @Qualifier("getMessageSender") MessageSender messageSender) {

        log.info("Handling InvoiceUpdatedEvent...");

        this.id = invoiceUpdatedEvent.id;
        this.tenantId = invoiceUpdatedEvent.tenantId;
        this.propertyId = invoiceUpdatedEvent.propertyId;
        this.status = String.valueOf(InvoiceStatus.UPDATED);
        this.dueDate = invoiceUpdatedEvent.dueDate;
        this.invoiceItems = invoiceUpdatedEvent.invoiceItems;

        String exchange = applicationConfig.getBillingMicroServiceExchange();
        String routingKey = applicationConfig.getBillingMicroServiceRoutingKey();

        messageSender.sendMessage(rabbitTemplate, exchange, invoiceUpdatedEvent);

        log.info("InvoiceUpdatedEvent published to the queue...");
    }
}
