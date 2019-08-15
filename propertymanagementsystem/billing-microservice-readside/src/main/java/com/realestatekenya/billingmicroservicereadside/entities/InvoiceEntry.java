package com.realestatekenya.billingmicroservicereadside.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "InvoiceEntries")
@Data
public class InvoiceEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private Double amount;
    private Integer quantity;
    private String description;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonIgnore
    private Invoice invoice;

    public InvoiceEntry() {
    }

    public InvoiceEntry(Double amount, Integer quantity, String description, Invoice invoice) {
        this.amount = amount;
        this.quantity = quantity;
        this.description = description;
        this.invoice = invoice;
    }
}
