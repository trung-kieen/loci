package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.mapper.ValueObjectTypeConverter;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { AuthorityEntityMapper.class, ValueObjectTypeConverter.class })
public interface MapStructUserEntityMapper {

  @Mapping(source = "id", target = "dbId")
  @Mapping(source = "publicId", target = "userPublicId")
  public User toDomain(UserEntity userEntity) ;


  @Mapping(source = "dbId", target = "id")
  @Mapping(source = "userPublicId", target = "publicId")
  public UserEntity from(User user);
}
