package com.realestatekenya.billingmicroservicereadside.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceCreatedDTO {

    private String id;
    private String tenantId;
    private String propertyId;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private List<InvoiceEntryDTO> invoiceItems;
}
