package com.realestatekenya.billingmicroservicewriteside.services;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BillingQueryServiceImpl implements BillingQueryService {

    private final EventStore eventStore;

    public BillingQueryServiceImpl(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public List<Object> listEventsForBillingMicroService(String billingId) {
        return eventStore.readEvents(billingId).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
    }
}
