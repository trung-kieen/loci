package com.loci.loci_backend.common.authentication.domain;

import java.util.stream.Stream;

import java.util.Collections;
import java.util.Set;

import com.loci.loci_backend.common.validation.domain.Assert;

public record Roles(Set<Role> roles) {
  public static final Roles EMPTY = new Roles(Collections.emptySet());

  public Roles(Set<Role> roles) {
    this.roles = Collections.unmodifiableSet(
        roles == null ? Collections.emptySet() : roles);
  }

  public boolean hasRole() {
    return !roles.isEmpty();
  }

  public boolean hasRole(Role role) {
    Assert.notNull("role", role);
    return roles.contains(role);
  }

  public Stream<Role> stream() {
    return this.roles.stream();
  }

  public Set<Role> get() {
    return roles();
  }

}
