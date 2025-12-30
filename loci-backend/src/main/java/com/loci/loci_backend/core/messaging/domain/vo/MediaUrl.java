package com.loci.loci_backend.core.messaging.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record MediaUrl(String value) implements ValueObject<String> {
}
