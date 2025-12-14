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
import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

  private String email;

  private String username;

  private String firstname;

  private String lastname;

  @Column(name = "profile_picture")
  private String profilePicture;

  @Column(name = "public_id", unique = true)
  private UUID publicId;

  private String bio;
  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
  private UserSettingsEntity settings;

  @Column(name = "last_active")
  private Instant lastActive;
  //
  // // Profile settings
  // @Column(name = "last_seen_setting")
  // @Enumerated(EnumType.STRING)
  // private LastSeenSettingEnum lastSeenSetting = LastSeenSettingEnum.EVERYONE;
  //
  // @Column(name = "friend_request_setting")
  // private FriendRequestSettingEnum friendRequestSetting =
  // FriendRequestSettingEnum.EVERYONE;

  // @Column(name = "profile_visibility")
  // private Boolean profileVisibility = true;

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(name = "user_authority", joinColumns = {
      @JoinColumn(name = "user_id", referencedColumnName = "id")
  }, inverseJoinColumns = {
      @JoinColumn(name = "authority_name", referencedColumnName = "name")

  })
  private Set<AuthorityEntity> authorities = new HashSet<>();

  @OneToMany(mappedBy = "owningUser", cascade = CascadeType.ALL)
  private Set<ContactEntity> contacts = new HashSet<>();

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
  private Set<MessageEntity> sentMessages = new HashSet<>();

  @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
  private Set<ConversationParticipantEntity> conversationMemberships = new HashSet<>();

  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
  private Set<ContactRequestEntity> receivedContactRequests = new HashSet<>();

  @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
  private Set<ContactRequestEntity> sentContactRequests = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<NotificationEntity> notifications = new HashSet<>();

  public String getUsername() {
    return username;

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
    // this.lastSeenSetting = lastSeenSetting;
    // this.friendRequestSetting = friendRequestSetting;
    // this.profileVisibility = profileVisibility;
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
