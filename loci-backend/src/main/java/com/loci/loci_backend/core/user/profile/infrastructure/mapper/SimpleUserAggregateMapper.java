package com.loci.loci_backend.core.user.profile.infrastructure.mapper;

import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.user.profile.domain.service.UserAggregateMapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
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
