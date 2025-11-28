package com.loci.loci_backend.core.user.infrastructure.mapper;

import com.loci.loci_backend.common.user.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.service.UserMigrationService;
import com.loci.loci_backend.common.user.domain.vo.UserPassword;
import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PrivacySetting;
import com.loci.loci_backend.core.user.domain.profile.service.UserAggregateMapper;

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

  @Override
  public KeycloakUser toKeycloakUser(User user) {
    return KeycloakUser.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .firstName(user.getFirstname())
        .lastName(user.getLastname())
        .build();
  }

}
