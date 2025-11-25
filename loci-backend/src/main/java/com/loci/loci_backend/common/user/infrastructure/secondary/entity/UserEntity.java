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
import com.loci.loci_backend.common.validation.infrastructure.EntityMapper;
import com.loci.loci_backend.core.user.domain.profile.aggregate.Fullname;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfileChanges;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PrivacySetting;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;
import com.loci.loci_backend.core.user.domain.profile.vo.FriendRequestSettingEnum;
import com.loci.loci_backend.core.user.domain.profile.vo.LastSeenSettingEnum;
import com.loci.loci_backend.core.user.domain.profile.vo.ProfileVisibility;
import com.loci.loci_backend.core.user.domain.profile.vo.UserFriendRequestSetting;
import com.loci.loci_backend.core.user.domain.profile.vo.UserLastSeenSetting;

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

  public static Page<PublicProfile> toDomain(Page<UserEntity> userPage) {
    Page<PublicProfile> profilePage = userPage.map((u) -> UserEntity.toPublicProfile(u));
    return profilePage;

  }

  public static User toDomain(UserEntity userEntity) {
    return User.builder()
        .email(new UserEmail(userEntity.getEmail()))
        .firstname(new UserFirstname(userEntity.getFirstname()))
        .lastname(new UserLastname(userEntity.getLastname()))
        .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .privacySetting(userEntity.getPrivacySetting())
        .dbId(userEntity.getId())
        .build();
  }

  public static PersonalProfile toPersonalProfile(UserEntity userEntity) {
    return PersonalProfile.builder()
        .fullname(
            Fullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .email(new UserEmail(userEntity.getEmail()))
        .username(new Username(userEntity.getUsername()))
        .userPublicId(new UserPublicId(userEntity.getPublicId()))
        .imageUrl(new UserImageUrl(userEntity.getImageURL()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
        .privacySetting(userEntity.getPrivacySetting())
        .dbId(userEntity.getId())
        .build();
  }

  public static PublicProfile toPublicProfile(UserEntity userEntity) {
    return PublicProfile.builder()
        .publicId(new UserPublicId(userEntity.getPublicId()))
        .username(new Username(userEntity.getUsername()))
        .fullname(
            Fullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .email(new UserEmail(userEntity.getEmail()))
        .imageUrl(new UserImageUrl(userEntity.getImageURL()))
        .createdDate(userEntity.getCreatedDate())
        .build();
  }

  public String getUsername() {
    return email;

  }

  public static UserEntity from(PersonalProfile profile) {
    UserEntityBuilder userEntityBuilder = UserEntity.builder();

    EntityMapper.applyIfPresent(profile::getImageUrl, i -> userEntityBuilder.imageURL(i.value()));

    EntityMapper.applyIfPresent(profile::getUserPublicId, i -> userEntityBuilder.publicId(i.value()));

    return userEntityBuilder
        .authorities(AuthorityEntity.from(profile.getAuthorities()))
        .email(profile.getEmail().value())
        .firstname(profile.getFullname().getFirstname().value())
        .lastname(profile.getFullname().getLastname().value())
        .lastSeen(profile.getLastSeen())
        .lastSeenSetting(profile.getPrivacySetting().getLastSeenSetting().value())
        .friendRequestSetting(profile.getPrivacySetting().getFriendRequestSetting().value())
        .profileVisibility(profile.getPrivacySetting().getProfileVisibility().value())
        .id(profile.getDbId())
        .build();
  }

  public void applyChanges(PersonalProfileChanges changes) {
    EntityMapper.applyIfPresent(changes::getFullname, fullname -> {
      EntityMapper.applyIfPresent(fullname::getFirstname, fn -> this.firstname = fn.value());
      EntityMapper.applyIfPresent(fullname::getLastname, ln -> this.lastname = ln.value());
    });

    EntityMapper.applyIfPresent(changes::getImageUrl, iu -> this.imageURL = iu.value());

    EntityMapper.applyIfPresent(changes::getPrivacySetting, ps -> {
      EntityMapper.applyIfPresent(ps::getLastSeenSetting, lss -> this.lastSeenSetting = lss.value());
      EntityMapper.applyIfPresent(ps::getFriendRequestSetting, frs -> this.friendRequestSetting = frs.value());
      EntityMapper.applyIfPresent(ps::getProfileVisibility, pv -> this.profileVisibility = pv.value());
    });
  }

  public static UserEntity from(User user) {
    UserEntity.UserEntityBuilder builder = UserEntity.builder()
        .authorities(AuthorityEntity.from(user.getAuthorities()))
        .email(user.getEmail().value())
        .firstname(user.getFirstname().value())
        .lastname(user.getLastname().value())
        .lastSeen(user.getLastSeen())
        .id(user.getDbId());
    EntityMapper.applyIfPresent(user::getPrivacySetting, ps -> {
      EntityMapper.applyIfPresent(ps::getLastSeenSetting, lss -> builder.lastSeenSetting(lss.value()));
      EntityMapper.applyIfPresent(ps::getFriendRequestSetting, frs -> builder.friendRequestSetting(frs.value()));
      EntityMapper.applyIfPresent(ps::getProfileVisibility, pv -> builder.profileVisibility(pv.value()));
    });

    EntityMapper.applyIfPresent(user::getImageUrl, iu -> builder.imageURL(iu.value()));
    EntityMapper.applyIfPresent(user::getUserPublicId, upi -> builder.publicId(upi.value()));

    // EntityMapper.applyIfPresent(user::getUserAddress, addr -> {
    // builder.addressCity(addr.city());
    // builder.addressCountry(addr.country());
    // builder.addressZipCode(addr.zipCode());
    // builder.addressStreet(addr.street());
    // });
    return builder.build();
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
