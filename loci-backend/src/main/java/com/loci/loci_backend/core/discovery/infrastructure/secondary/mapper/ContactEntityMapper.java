package com.loci.loci_backend.core.discovery.infrastructure.secondary.mapper;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.discovery.domain.aggregate.Contact;
import com.loci.loci_backend.core.discovery.domain.aggregate.ContactBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactEntityMapper {

  public Page<Contact> toDomain(Page<UserEntity> entityPage) {
    return entityPage.map(this::toDomain);
  }

  public Contact toDomain(UserEntity entity) {
    UserFullname fullname = UserFullname.builder()
        .firstname(new UserFirstname(entity.getFirstname()))
        .lastname(new UserLastname(entity.getLastname()))
        .build();
    return ContactBuilder.contact()
        .publicId(new UserPublicId(entity.getPublicId())).fullname(fullname)
        .username(new Username(entity.getUsername()))
        .userEmail(new UserEmail(entity.getEmail()))
        .imageUrl(new UserImageUrl(entity.getProfilePicture()))
        // TODO: fetch friendship status
        .friendshipStatus(null)
        .build();
  }

}
