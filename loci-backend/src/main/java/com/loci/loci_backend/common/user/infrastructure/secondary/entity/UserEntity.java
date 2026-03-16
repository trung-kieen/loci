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

package com.loci.loci_backend.common.user.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.conversation.infrastructure.secondary.entity.ConversationParticipantEntity;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.messaging.infrastructure.secondary.entity.MessageEntity;
import com.loci.loci_backend.core.notification.infrastructure.secondary.entity.NotificationEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactRequestEntity;

import org.jilt.Builder;
import org.jilt.BuilderStyle;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_")
@Setter
@Getter
@NoArgsConstructor
public class UserEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String username;

  private String firstname;

  private String lastname;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "public_id", unique = true)
  private UUID publicId;

  private String bio;

  @Column(name = "last_active")
  private Instant lastActive;

  @ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY)
  @JoinTable(name = "user_authority", joinColumns = {
      @JoinColumn(name = "user_id", referencedColumnName = "id")
  }, inverseJoinColumns = {
      @JoinColumn(name = "authority_name", referencedColumnName = "name")

  })
  private Set<AuthorityEntity> authorities = new HashSet<>();

  @OneToMany(mappedBy = "owningUser", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<ContactEntity> contacts = new HashSet<>();

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<MessageEntity> sentMessages = new HashSet<>();

  @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<ConversationParticipantEntity> conversationMemberships = new HashSet<>();

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<ContactRequestEntity> receivedContactRequests = new HashSet<>();

  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<ContactRequestEntity> sentContactRequests = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private Set<NotificationEntity> notifications = new HashSet<>();

  public String getUsername() {
    return username;

  }

  public Set<AuthorityEntity> getAuthorities() {
    return authorities;
  }

  @Builder(style = BuilderStyle.STAGED)
  public UserEntity(UUID publicId, Long id, String email, String firstname, String lastname, String username,
      String profilePicture,
      String bio, Instant lastActive, Set<AuthorityEntity> authorities) {
    this.publicId = publicId;
    this.id = id;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.profilePicture = profilePicture;
    this.bio = bio;
    this.authorities = authorities;
  }

  public UserFullname getFullname() {
    return UserFullname.from(new UserFirstname(firstname), new UserLastname(this.lastname));
  }

  /**
   * Apply profile changes if field is not null
   */
  public void applyChanges(PersonalProfileChanges changes) {
    NullSafe.applyIfPresent(changes::getFullname, fullname -> {
      NullSafe.applyIfPresent(fullname::getFirstname, fn -> this.firstname = fn.value());
      NullSafe.applyIfPresent(fullname::getLastname, ln -> this.lastname = ln.value());
    });

    NullSafe.applyIfPresent(changes::getBio, iu -> this.bio = iu.value());
    NullSafe.applyIfPresent(changes::getImageUrl, iu -> this.profilePicture = iu.value());
    NullSafe.applyIfPresent(changes::getImageUrl, iu -> this.profilePicture = iu.value());

  }

  @Override
  public Long getId() {
    return id;
  }

}
