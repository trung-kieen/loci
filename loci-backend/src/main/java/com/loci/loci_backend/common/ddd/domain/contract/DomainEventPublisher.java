package com.loci.loci_backend.common.ddd.domain.contract;

import java.io.Serializable;

public interface DomainEventPublisher {
    void publish(Serializable event);
}
