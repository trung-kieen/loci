package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record MessageId(Long value) implements ValueObject<Long> {

}
