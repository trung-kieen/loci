package com.loci.loci_backend.core.identity.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record ProfileBio(String value) implements ValueObject<String> {
}
