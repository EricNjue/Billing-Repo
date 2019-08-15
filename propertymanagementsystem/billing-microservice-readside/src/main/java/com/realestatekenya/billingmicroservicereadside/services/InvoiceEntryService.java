package com.realestatekenya.billingmicroservicereadside.services;

import com.realestatekenya.billingmicroservicereadside.entities.InvoiceEntry;

import java.util.List;

public interface InvoiceEntryService {
    List<InvoiceEntry> getInvoiceEntries(String invoiceId);
    InvoiceEntry getInvoiceEntry(Long id);
}
