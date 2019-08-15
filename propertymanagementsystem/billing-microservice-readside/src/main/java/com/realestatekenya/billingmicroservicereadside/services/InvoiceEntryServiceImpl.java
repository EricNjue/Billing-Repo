package com.realestatekenya.billingmicroservicereadside.services;

import com.realestatekenya.billingmicroservicereadside.entities.InvoiceEntry;
import com.realestatekenya.billingmicroservicereadside.repositories.InvoiceEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceEntryServiceImpl implements InvoiceEntryService {

    private final InvoiceEntryRepository invoiceEntryRepository;

    public InvoiceEntryServiceImpl(InvoiceEntryRepository invoiceEntryRepository) {
        this.invoiceEntryRepository = invoiceEntryRepository;
    }

    @Override
    public List<InvoiceEntry> getInvoiceEntries(String invoiceId) {
        return invoiceEntryRepository.findAllByInvoiceId(invoiceId);
    }

    @Override
    public InvoiceEntry getInvoiceEntry(Long id) {
        return invoiceEntryRepository.findById(id).orElse(null);
    }
}
