package com.loci.loci_backend.common.user.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.Fullname;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.vo.FriendRequestSettingEnum;
import com.loci.loci_backend.core.identity.domain.vo.LastSeenSettingEnum;
import com.loci.loci_backend.core.identity.domain.vo.ProfileVisibility;
import com.loci.loci_backend.core.identity.domain.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.identity.domain.vo.UserLastSeenSetting;

import org.springframework.data.domain.Page;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USER_")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column
  private String email;

  @Column
  private String firstname;

  private String lastname;

  @Column(name = "image_url")
  private String imageURL;

  @Column(name = "public_id")
  private UUID publicId;

  @Column(name = "last_seen")
  private Instant lastSeen;

  @Column(name = "last_seen_setting")
  @Enumerated(EnumType.STRING)
  private LastSeenSettingEnum lastSeenSetting = LastSeenSettingEnum.EVERYONE;

  @Column(name = "friend_request_setting")
  private FriendRequestSettingEnum friendRequestSetting = FriendRequestSettingEnum.EVERYONE;

  @Column(name = "profile_visibility")
  private Boolean profileVisibility = true;

  @Column(name = "bio")
  private String bio;

  @Column(name = "last_active")
  private Instant lastActive;

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(name = "user_authority", joinColumns = {
      @JoinColumn(name = "user_id", referencedColumnName = "id")
  },

      inverseJoinColumns = {
          @JoinColumn(name = "authority_name", referencedColumnName = "name")

      }

  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  @Column
  @Enumerated(EnumType.STRING)
  private Gender gender;

  public enum Gender {
    MALE, FEMALE
  }

  public String getUsername() {
    return email;

  }

  public void applyChanges(PersonalProfileChanges changes) {
    NullSafe.applyIfPresent(changes::getFullname, fullname -> {
      NullSafe.applyIfPresent(fullname::getFirstname, fn -> this.firstname = fn.value());
      NullSafe.applyIfPresent(fullname::getLastname, ln -> this.lastname = ln.value());
    });

    NullSafe.applyIfPresent(changes::getImageUrl, iu -> this.imageURL = iu.value());

    NullSafe.applyIfPresent(changes::getPrivacySetting, ps -> {
      NullSafe.applyIfPresent(ps::getLastSeenSetting, lss -> this.lastSeenSetting = lss.value());
      NullSafe.applyIfPresent(ps::getFriendRequestSetting, frs -> this.friendRequestSetting = frs.value());
      NullSafe.applyIfPresent(ps::getProfileVisibility, pv -> this.profileVisibility = pv.value());
    });
  }

  public PrivacySetting getPrivacySetting() {
    return PrivacySetting.builder()
        .lastSeenSetting(UserLastSeenSetting.ofEnum(this.lastSeenSetting))
        .friendRequestSetting(UserFriendRequestSetting.ofEnum(this.friendRequestSetting))
        .profileVisibility(ProfileVisibility.of(this.profileVisibility))
        .build();
  }

  @Override
  public Long getId() {
    return id;
  }

}
