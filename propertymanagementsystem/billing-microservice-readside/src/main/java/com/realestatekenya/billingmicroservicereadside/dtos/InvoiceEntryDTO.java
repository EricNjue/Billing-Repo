package com.realestatekenya.billingmicroservicereadside.dtos;

import lombok.Data;

@Data
public class InvoiceEntryDTO {

    private String description;
    private Integer quantity;
    private Double amount;
}
