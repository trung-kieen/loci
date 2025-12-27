package com.loci.loci_backend.core.identity.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.mapper.AuthorityEntityMapper;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSettings;
import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ValueObjectTypeConverter.class, AuthorityEntityMapper.class })
public interface MapStructIdentityEntityMapper {

  @Mapping(source = "profilePicture", target = "imageUrl")
  @Mapping(source = "email", target = "userEmail")
  @Mapping(source = "id", target = "dbId")
  public UserSummary toUserSummary(UserEntity entity);

  @Mapping(source = "id", target = "dbId")
  @Mapping(source = "profilePicture", target = "imageUrl")
  @Mapping(source = "publicId", target = "userPublicId")
  public PersonalProfile toPersonalProfile(UserEntity userEntity);

  @Mapping(source = "userEntity.id", target = "userDBId")
  @Mapping(source = "userEntity.profilePicture", target = "imageUrl")
  @Mapping(target = "connectionStatus", ignore = true)
  public PublicProfile toPublicProfile(UserEntity userEntity);

  @Mapping(target = "contacts", ignore = true)
  @Mapping(target = "notifications", ignore = true)
  @Mapping(target = "conversationMemberships", ignore = true)
  @Mapping(target = "receivedContactRequests", ignore = true)
  @Mapping(target = "sentContactRequests", ignore = true)
  @Mapping(target = "sentMessages", ignore = true)
  @Mapping(source = "fullname.firstname", target = "firstname")
  @Mapping(source = "fullname.lastname", target = "lastname")
  @Mapping(source = "imageUrl", target = "profilePicture")
  @Mapping(source = "dbId", target = "id")
  @Mapping(source = "userPublicId", target = "publicId")
  public UserEntity from(PersonalProfile profile);

  @Mapping(source = "id", target = "userId")
  public UserSettings toDomain(UserSettingsEntity settings);

  @Mapping(source = "userId", target = "id")
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  public UserSettingsEntity from(UserSettings domainObjectt);
}
