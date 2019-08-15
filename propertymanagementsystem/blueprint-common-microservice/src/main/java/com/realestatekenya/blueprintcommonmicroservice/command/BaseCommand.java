package com.realestatekenya.blueprintcommonmicroservice.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;


// All Commands should be Immutable ...

public class BaseCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
