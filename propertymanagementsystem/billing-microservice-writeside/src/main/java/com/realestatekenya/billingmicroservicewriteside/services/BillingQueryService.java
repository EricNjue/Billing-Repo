package com.realestatekenya.billingmicroservicewriteside.services;

import java.util.List;

public interface BillingQueryService {
    List<Object> listEventsForBillingMicroService(String billingId);
}
