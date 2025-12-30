package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record MediaName(String value) implements ValueObject<String> {
}
