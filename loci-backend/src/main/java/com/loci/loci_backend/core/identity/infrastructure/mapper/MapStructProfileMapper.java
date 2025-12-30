package com.loci.loci_backend.core.identity.infrastructure.mapper;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;

import org.mapstruct.BeanMapping;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MapStructProfileMapper {
  @Condition
  default boolean isWrapperPresent(ValueObject<?> input) {
    return NullSafe.isPresent(input);
  }


  @Mapping(source = "fullname", target = "fullname")
  public void applyProfileUpdatePartial(@MappingTarget PersonalProfile profile, PersonalProfileChanges changes);

  // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "userId", ignore = true)
  public void applySettingsUpdatePartial(@MappingTarget UserSettings settings,
      ProfileSettingChanges changes);


}
