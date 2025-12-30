package com.loci.loci_backend.core.identity.domain.vo;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.user.domain.vo.PublicId;

public record UserPresenceId(PublicId value) implements ValueObject<PublicId>{
}
