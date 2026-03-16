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

package com.loci.loci_backend.core.identity.infrastructure.secondary.entity;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.FriendRequestSettingEnum;
import com.loci.loci_backend.core.identity.infrastructure.secondary.enumeration.LastSeenSettingEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Map one to one unbidirectional with user entity
 */
@Entity
@Table(name = "user_setting")
@Getter
@Setter
@NoArgsConstructor
public class UserSettingEntity extends AbstractAuditingEntity<Long> {

  @Id
  private Long userId;

  // create contraint to fk from user
  @OneToOne(fetch = FetchType.LAZY)
  // @MapsId // make join column map value to id field
  @JoinColumn(name = "user_id") // mark primary field name as user_id
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private UserEntity user;

  @Column(name = "last_seen_setting")
  @Enumerated(EnumType.STRING)
  private LastSeenSettingEnum lastSeenSetting = LastSeenSettingEnum.EVERYONE;

  @Column(name = "friend_request_setting")
  @Enumerated(EnumType.STRING)
  private FriendRequestSettingEnum friendRequestSetting = FriendRequestSettingEnum.EVERYONE;

  @Column(name = "profile_visibility")
  private Boolean profileVisibility = true;

  public void setUser(UserEntity user) {

    // JPA required not direct assign maping id to primary key to avoid => use
    // EntityManager if want to mannualy maping id instead of MapsId
    // ObjectOptimisticLockingFailureException
    this.userId = user.getId();
    // this.user = user;
  }

  @Override
  public Long getId() {
    return this.userId;
  }

  // public UserSettingsEntity(UserEntity user) {
  // this.user = user;
  // this.id = user.getId();
  // }
}
