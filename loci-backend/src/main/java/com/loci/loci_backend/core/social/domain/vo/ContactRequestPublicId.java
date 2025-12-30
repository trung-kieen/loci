package com.loci.loci_backend.core.social.domain.vo;

import java.util.UUID;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record ContactRequestPublicId (UUID value) implements ValueObject<UUID>{


}
