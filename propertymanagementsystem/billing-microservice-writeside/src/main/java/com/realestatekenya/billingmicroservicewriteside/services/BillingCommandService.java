package com.realestatekenya.billingmicroservicewriteside.services;

import com.realestatekenya.billingmicroservicewriteside.dtos.CreateInvoiceDTO;
import com.realestatekenya.billingmicroservicewriteside.dtos.UpdateInvoiceDTO;

import java.util.concurrent.CompletableFuture;

public interface BillingCommandService {

    CompletableFuture<String> createInvoice(CreateInvoiceDTO createInvoiceDTO);

    CompletableFuture<String> updateInvoice(String invoiceId, UpdateInvoiceDTO updateInvoiceDTO);
}
