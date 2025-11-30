package com.loci.loci_backend.common.user.infrastructure.secondary.entity;

import java.io.Serializable;
import java.util.Collection;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Data
public class AuthorityEntity implements Serializable {
  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  public String name;


}
