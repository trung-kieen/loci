package com.loci.loci_backend.core.notification.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.STOMPNotification;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructSTOMPNotificationMapper {

  STOMPNotification from(Notification domain);

}
