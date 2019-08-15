package com.realestatekenya.billingmicroservicereadside.repositories;

import com.realestatekenya.billingmicroservicereadside.entities.InvoiceEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceEntryRepository extends CrudRepository<InvoiceEntry, Long> {
    List<InvoiceEntry> findAllByInvoiceId(String invoiceId);
}
