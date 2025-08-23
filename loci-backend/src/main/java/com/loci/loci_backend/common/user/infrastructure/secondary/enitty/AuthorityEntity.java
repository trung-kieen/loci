package com.loci.loci_backend.common.user.infrastructure.secondary.enitty;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "authority")
@Builder
@Data
public class AuthorityEntity implements Serializable {
  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  public String name;

  public AuthorityEntity(String name) {
    this.name = name;
  }

  public static Set<AuthorityEntity> from(Set<Authority> authorities) {
    return authorities.stream().map(AuthorityEntity::from).collect(Collectors.toSet());
  }

  public static AuthorityEntity from(Authority authority) {
    return AuthorityEntity.builder()
        .name(authority.getName().name())
        .build();
  }

  public static Authority toDomain(AuthorityEntity entity) {
    return Authority.builder()
        .name(new AuthorityName(entity.getName()))
        .build();
  }

  public static Set<Authority> toDomain(Set<AuthorityEntity> entities) {
    return entities.stream().map(AuthorityEntity::toDomain).collect(Collectors.toSet());
  }
}
