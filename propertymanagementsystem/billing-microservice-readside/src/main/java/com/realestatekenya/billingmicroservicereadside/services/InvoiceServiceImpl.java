package com.realestatekenya.billingmicroservicereadside.services;

import com.realestatekenya.billingmicroservicereadside.entities.Invoice;
import com.realestatekenya.billingmicroservicereadside.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice getInvoice(String invoiceId) {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }
}
