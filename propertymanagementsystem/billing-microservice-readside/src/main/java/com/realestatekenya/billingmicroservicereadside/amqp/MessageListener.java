package com.realestatekenya.billingmicroservicereadside.amqp;

import com.realestatekenya.billingmicroservicereadside.config.ApplicationConfigReader;
import com.realestatekenya.billingmicroservicereadside.dtos.InvoiceCreatedDTO;
import com.realestatekenya.billingmicroservicereadside.dtos.InvoiceEntryDTO;
import com.realestatekenya.billingmicroservicereadside.dtos.InvoiceUpdatedDTO;
import com.realestatekenya.billingmicroservicereadside.entities.Invoice;
import com.realestatekenya.billingmicroservicereadside.entities.InvoiceEntry;
import com.realestatekenya.billingmicroservicereadside.repositories.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageListener {

    @Autowired
    ApplicationConfigReader applicationConfig;

    @Autowired
    private final InvoiceRepository invoiceRepository;

    public MessageListener(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @RabbitListener(queues = "${billing-microservice.queue.name}")
    @Transactional
    public void receiveInvoiceCreatedEvent(InvoiceCreatedDTO invoiceCreatedDTO) {
        log.info(String.format("InvoiceCreatedDTO Object received:- %s", invoiceCreatedDTO));
        Invoice invoice = new Invoice();

        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Double payableAmount = 0.0D;

        if (invoiceCreatedDTO.getInvoiceItems() != null && invoiceCreatedDTO.getInvoiceItems().size() > 0) {
            for (InvoiceEntryDTO invoiceEntryDTO : invoiceCreatedDTO.getInvoiceItems()) {
                InvoiceEntry invoiceEntry = new InvoiceEntry(invoiceEntryDTO.getAmount(),
                        invoiceEntryDTO.getQuantity(), invoiceEntryDTO.getDescription(), invoice);
                invoiceEntries.add(invoiceEntry);
                payableAmount += (invoiceEntry.getQuantity() * invoiceEntry.getAmount());
            }
        }


        Invoice invoiceInDb = invoiceRepository.findById(invoiceCreatedDTO.getId()).orElse(null);

        if ((invoiceInDb != null) && (invoiceInDb.getPaidAmount() == 0)) {
            log.info("Invoice exists. Removing entries for recreation...");
            invoiceRepository.delete(invoiceInDb);
        }

        invoice.setId(invoiceCreatedDTO.getId());
        invoice.setTenantId(invoiceCreatedDTO.getTenantId());
        invoice.setPropertyId(invoiceCreatedDTO.getPropertyId());
        invoice.setCreationDate(invoiceCreatedDTO.getCreationDate());
        invoice.setDueDate(invoiceCreatedDTO.getDueDate());
        invoice.setPaidAmount(0D);
        invoice.setBalance(payableAmount);
        invoice.setInvoiceEntries(invoiceEntries);

        invoiceRepository.save(invoice);
        log.info("Invoice details saved...");
    }
}
