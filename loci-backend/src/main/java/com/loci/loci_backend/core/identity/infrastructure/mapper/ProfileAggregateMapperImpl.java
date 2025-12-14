package com.loci.loci_backend.core.identity.infrastructure.mapper;

import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChangesBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.service.ProfileAggregateMapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileAggregateMapperImpl implements ProfileAggregateMapper {
  private final MapStructProfileMapper profileMapper;

  @Override
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile) {
    return PersonalProfileChangesBuilder.personalProfileChanges()
        .fullname(currentProfile.getFullname())
        .bio(currentProfile.getBio())
        .imageUrl(currentProfile.getImageUrl())
        .build();
  }

  // TODO: check the logic of partial update correct
  @Override
  public void applyChanges(PersonalProfile profile, PersonalProfileChanges changes) {
    profileMapper.applyProfileUpdatePartial(profile, changes);
  }

  @Override
  public void applyChanges(UserSettings settings, ProfileSettingChanges changes) {
    profileMapper.applySettingsUpdatePartial(settings, changes);
  }

}
