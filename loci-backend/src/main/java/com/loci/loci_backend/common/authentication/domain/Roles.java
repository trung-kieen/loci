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
