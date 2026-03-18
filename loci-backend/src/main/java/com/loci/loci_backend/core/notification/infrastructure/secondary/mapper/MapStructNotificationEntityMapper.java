package com.loci.loci_backend.core.notification.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.ddd.infrastructure.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.notification.domain.aggregate.Notification;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.NotificationEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructNotificationEntityMapper {

  @Mapping(source = "notificationId", target = "id")
  NotificationEntity from(Notification domain);

  @Mapping(source = "id", target = "notificationId")
  @Mapping(source = "createdDate", target = "createAt")
  Notification toDomain(NotificationEntity restModel);

}
