/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loci.loci_backend.core.identity.domain.repository;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSetting;

public interface ProfileRepository {
  PersonalProfile findPersonalProfile(UserEmail userEmail);

  PublicProfile findPublicProfileByUserIdOrUserName(PublicId userId, Username username);

  PublicProfile findPublicProfileById(UserDBId dbId);

  PublicProfile findPublicProfileUserName(Username username);

  PersonalProfile applyProfileUpdate(Username username, PersonalProfileChanges profileChanges);

  UserSetting findProfileSettings(UserDBId dbId);

  PersonalProfile save(PersonalProfile profile);

  UserSetting save(UserSetting settings);

}
