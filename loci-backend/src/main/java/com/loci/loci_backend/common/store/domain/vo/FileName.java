package com.loci.loci_backend.common.store.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record FileName(String value) implements ValueObject<String> {
}
