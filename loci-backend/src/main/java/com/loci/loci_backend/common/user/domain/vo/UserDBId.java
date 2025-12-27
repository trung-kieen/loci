package com.loci.loci_backend.common.user.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;

public record UserDBId(Long value) implements ValueObject<Long> {
}
