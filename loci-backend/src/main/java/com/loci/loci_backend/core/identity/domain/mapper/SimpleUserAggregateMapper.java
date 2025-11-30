package com.loci.loci_backend.core.identity.domain.mapper;

import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.identity.domain.service.UserAggregateMapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SimpleUserAggregateMapper implements UserAggregateMapper {
  private final ModelMapper mapper;

  @Override
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile) {
    return PersonalProfileChanges.builder()
        .fullname(currentProfile.getFullname())
        .imageUrl(currentProfile.getImageUrl())
        .privacySetting(PrivacySetting.of(currentProfile.getPrivacySetting()))
        .build();
    // return mapper.map(currentProfile, PersonalProfileChanges.class);
  }

}
