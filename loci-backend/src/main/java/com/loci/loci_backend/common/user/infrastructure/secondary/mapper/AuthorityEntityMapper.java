package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.mapper.DomainEntityMapper;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.AuthorityEntity;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityEntityMapper implements DomainEntityMapper<Authority, AuthorityEntity> {

  public Set<AuthorityEntity> from(Collection<Authority> authorities) {
    return authorities.stream().map(this::from).collect(Collectors.toSet());
  }

  public AuthorityEntity from(Authority authority) {
    return AuthorityEntity.builder()
        .name(authority.getName().name().toUpperCase())
        .build();
  }

  public Authority toDomain(AuthorityEntity entity) {
    return Authority.builder()
        .name(new AuthorityName(entity.getName()))
        .build();
  }

  public Set<Authority> toDomain(Collection<AuthorityEntity> entities) {
    return entities.stream().map(this::toDomain).collect(Collectors.toSet());
  }

}
