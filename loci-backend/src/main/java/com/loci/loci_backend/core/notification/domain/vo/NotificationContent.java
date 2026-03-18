package com.loci.loci_backend.core.notification.domain.vo;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;

public record NotificationContent(String value) implements ValueObject<String>{
}
