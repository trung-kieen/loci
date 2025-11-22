package com.loci.loci_backend.core.user.domain.profile.repository;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;

public interface ProfileRepository {
    PersonalProfile findPersonalProfile(UserEmail userEmail);
    PublicProfile findPublicProfileByUserIdOrUserName(UserPublicId userId, Username username);
    PublicProfile findPublicProfileUserName(Username username);
}
