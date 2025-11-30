package com.loci.loci_backend.core.identity.domain.service;

import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;

public interface UserAggregateMapper {
  public PersonalProfileChanges extractChanges(PersonalProfile currentProfile);
}
