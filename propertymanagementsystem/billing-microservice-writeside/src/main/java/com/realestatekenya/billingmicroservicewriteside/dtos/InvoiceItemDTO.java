package com.realestatekenya.billingmicroservicewriteside.dtos;

import lombok.Data;

@Data
public class InvoiceItemDTO {

    private String description;
    private Integer quantity;
    private Double amount;
}
