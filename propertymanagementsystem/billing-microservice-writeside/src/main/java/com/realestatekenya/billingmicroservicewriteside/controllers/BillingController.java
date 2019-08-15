package com.realestatekenya.billingmicroservicewriteside.controllers;

import com.realestatekenya.billingmicroservicewriteside.dtos.CreateInvoiceDTO;
import com.realestatekenya.billingmicroservicewriteside.dtos.UpdateInvoiceDTO;
import com.realestatekenya.billingmicroservicewriteside.services.BillingCommandService;
import com.realestatekenya.billingmicroservicewriteside.services.BillingQueryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/billing")
@Slf4j
@Api(value = "Billing Commands", description = "Billing Commands Related Endpoints",
        tags = "Billing Commands")
public class BillingController {

    private final BillingCommandService billingCommandService;
    private final BillingQueryService billingQueryService;

    public BillingController(BillingCommandService billingCommandService, BillingQueryService billingQueryService) {
        this.billingCommandService = billingCommandService;
        this.billingQueryService = billingQueryService;
    }

    @PostMapping(value = "/create-invoice")
    public CompletableFuture<String> createInvoice(@RequestBody CreateInvoiceDTO createInvoiceDTO) {
        return billingCommandService.createInvoice(createInvoiceDTO);
    }

    @PutMapping(value = "/{invoiceId}/update-invoice")
    public CompletableFuture<String> updateInvoice(@PathVariable(value = "invoiceId") String invoiceId,
                                                   @RequestBody UpdateInvoiceDTO updateInvoiceDTO) {
        return billingCommandService.updateInvoice(invoiceId, updateInvoiceDTO);
    }

    @GetMapping(value = "/{invoiceId}/events")
    public ResponseEntity<List<Object>> getEventsForBilling(@PathVariable(value = "invoiceId") String invoiceId) {
        return ResponseEntity.ok(billingQueryService.listEventsForBillingMicroService(invoiceId));
    }
}
