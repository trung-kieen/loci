package com.loci.loci_backend.core.user.profile.domain.repository;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.profile.domain.vo.UserSearchCriteria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileRepository {
  PersonalProfile findPersonalProfile(UserEmail userEmail);

  PublicProfile findPublicProfileByUserIdOrUserName(UserPublicId userId, Username username);

  PublicProfile findPublicProfileUserName(Username username);

  PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges);

  Page<PublicProfile> searchActiveUsers(UserSearchCriteria criteria, Pageable pageable);
}
