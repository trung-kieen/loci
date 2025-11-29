package com.loci.loci_backend.core.user.profile.domain.service;

import com.loci.loci_backend.common.migration.domain.aggregate.KeycloakUser;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfileChanges;

public interface UserAggregateMapper {
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile);
}
