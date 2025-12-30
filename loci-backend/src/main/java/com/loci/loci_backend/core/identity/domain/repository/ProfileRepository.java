package com.loci.loci_backend.core.identity.domain.repository;

import java.util.Optional;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;

public interface ProfileRepository {
  PersonalProfile findPersonalProfile(UserEmail userEmail);

  PublicProfile findPublicProfileByUserIdOrUserName(PublicId userId, Username username);

  PublicProfile findPublicProfileById(UserDBId dbId);

  PublicProfile findPublicProfileUserName(Username username);

  PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges);

  UserSettings findProfileSettings(UserDBId dbId);

  UserSettings save(UserSettings settings);

}
