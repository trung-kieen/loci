package com.loci.loci_backend.core.identity.infrastructure.primary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettingsPatch;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructRestProfileMapper {
  public ProfileSettingChanges toDomain(RestProfileSettingsPatch patchRequest);

  @Mapping(source = "friendRequestSetting", target = "friendRequests")
  public RestProfileSettings from(UserSettings profile);

}
