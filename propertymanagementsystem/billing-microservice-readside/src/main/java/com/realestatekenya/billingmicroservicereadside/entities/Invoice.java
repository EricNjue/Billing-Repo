package com.realestatekenya.billingmicroservicereadside.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Invoices")
@Data
public class Invoice {

    @Id
    private String id;
    private String tenantId;
    private String propertyId;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private Double paidAmount;
    private Double balance;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<InvoiceEntry> invoiceEntries;

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", paidAmount=" + paidAmount +
                ", balance=" + balance +
                '}';
    }
}
