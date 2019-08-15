package com.realestatekenya.billingmicroservicewriteside.dtos;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateInvoiceDTO {
    private String tenantId;
    private String propertyId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;
    private List<InvoiceItemDTO> invoiceItems;
}
