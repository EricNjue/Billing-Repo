package com.realestatekenya.billingmicroservicewriteside.commands;

import com.realestatekenya.billingmicroservicewriteside.dtos.InvoiceItemDTO;
import com.realestatekenya.blueprintcommonmicroservice.command.BaseCommand;

import java.time.LocalDate;
import java.util.List;

public class UpdateInvoiceCommand extends BaseCommand<String> {
    public final String tenantId;
    public final String propertyId;
    public final LocalDate dueDate;
    public final List<InvoiceItemDTO> invoiceItems;

    public UpdateInvoiceCommand(String id, String tenantId, String propertyId, LocalDate dueDate,
                                List<InvoiceItemDTO> invoiceItems) {
        super(id);
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.dueDate = dueDate;
        this.invoiceItems = invoiceItems;
    }
}
