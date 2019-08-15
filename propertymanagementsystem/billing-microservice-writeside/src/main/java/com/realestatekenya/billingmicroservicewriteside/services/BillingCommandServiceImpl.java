package com.realestatekenya.billingmicroservicewriteside.services;

import com.realestatekenya.billingmicroservicewriteside.commands.CreateInvoiceCommand;
import com.realestatekenya.billingmicroservicewriteside.commands.UpdateInvoiceCommand;
import com.realestatekenya.billingmicroservicewriteside.dtos.CreateInvoiceDTO;
import com.realestatekenya.billingmicroservicewriteside.dtos.UpdateInvoiceDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BillingCommandServiceImpl implements BillingCommandService {

    private final CommandGateway commandGateway;

    public BillingCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createInvoice(CreateInvoiceDTO createInvoiceDTO) {
        return commandGateway.send(new CreateInvoiceCommand(UUID.randomUUID().toString(), createInvoiceDTO.getTenantId()
                , createInvoiceDTO.getPropertyId(), createInvoiceDTO.getDueDate(), createInvoiceDTO.getInvoiceItems()));
    }

    @Override
    public CompletableFuture<String> updateInvoice(String invoiceId, UpdateInvoiceDTO updateInvoiceDTO) {
        return commandGateway.send(new UpdateInvoiceCommand(invoiceId, updateInvoiceDTO.getTenantId()
                , updateInvoiceDTO.getPropertyId(), updateInvoiceDTO.getDueDate(), updateInvoiceDTO.getInvoiceItems()));
    }
}
