package com.realestatekenya.billingmicroservicereadside.repositories;

import com.realestatekenya.billingmicroservicereadside.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, String> {
}
