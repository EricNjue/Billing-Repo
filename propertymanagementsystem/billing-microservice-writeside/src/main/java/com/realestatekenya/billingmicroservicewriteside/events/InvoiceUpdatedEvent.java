package com.realestatekenya.billingmicroservicewriteside.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.realestatekenya.billingmicroservicewriteside.dtos.InvoiceItemDTO;
import com.realestatekenya.blueprintcommonmicroservice.event.BaseEvent;

import java.time.LocalDate;
import java.util.List;

public class InvoiceUpdatedEvent extends BaseEvent<String> {
    public final String tenantId;
    public final String propertyId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    public final LocalDate dueDate;
    public final List<InvoiceItemDTO> invoiceItems;

    public InvoiceUpdatedEvent(String id, String tenantId, String propertyId, LocalDate dueDate,
                               List<InvoiceItemDTO> invoiceItems) {
        super(id);
        this.tenantId = tenantId;
        this.propertyId = propertyId;
        this.dueDate = dueDate;
        this.invoiceItems = invoiceItems;

    }
}
