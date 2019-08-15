package com.realestatekenya.billingmicroservicereadside.services;

import com.realestatekenya.billingmicroservicereadside.entities.Invoice;

public interface InvoiceService {
    Invoice getInvoice(String invoiceId);
}
