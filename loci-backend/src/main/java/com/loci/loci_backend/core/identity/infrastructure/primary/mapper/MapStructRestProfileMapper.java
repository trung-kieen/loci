package com.loci.loci_backend.core.identity.infrastructure.primary.mapper;

import java.time.Instant;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.util.TimeFormatter;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.ProfileSettingChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfile;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPersonalProfilePatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettings;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestProfileSettingsPatch;
import com.loci.loci_backend.core.identity.infrastructure.primary.payload.RestPublicProfile;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class })
public interface MapStructRestProfileMapper {
  @Mapping(source = "friendRequests", target = "friendRequestSetting")
  public ProfileSettingChanges toDomain(RestProfileSettingsPatch patchRequest);

  @Mapping(source = "friendRequestSetting", target = "friendRequests")
  public RestProfileSettings from(UserSettings profile);

  @Mapping(source = "email", target = "emailAddress")
  @Mapping(source = "fullname.firstname", target = "firstname")
  @Mapping(source = "fullname.lastname", target = "lastname")
  @Mapping(source = "imageUrl", target = "profilePictureUrl")
  public RestPersonalProfile from(PersonalProfile profile);

  @Mapping(source = "profilePictureUrl", target = "imageUrl")
  public PersonalProfileChanges toDomain(RestPersonalProfilePatch patch);

  @Mapping(source = "email", target = "emailAddress")
  @Mapping(source = "imageUrl", target = "profilePictureUrl")
  @Mapping(source = "createdDate", target = "createdAt")
  @Mapping(target = "memberSince", ignore = true)
  // @Mapping(target = "memberSince", qualifiedByName = "formatTimeAgo")
  public RestPublicProfile from(PublicProfile profile);

  // @Named("formatTimeAgo")
  // default String formatTimeAgo(Instant createdDate) {
  //   return TimeFormatter.timeAgo(createdDate);
  // }
}
