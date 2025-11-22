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
import com.loci.loci_backend.core.user.domain.profile.aggregate.Fullname;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PersonalProfile;
import com.loci.loci_backend.core.user.domain.profile.aggregate.PublicProfile;

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

  public static User toDomain(UserEntity userEntity) {
    return User.builder()
        .email(new UserEmail(userEntity.getEmail()))
        .firstname(new UserFirstname(userEntity.getFirstname()))
        .lastname(new UserLastname(userEntity.getLastname()))
        .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .dbId(userEntity.getId())
        .build();
  }

  public static PersonalProfile toPersonalProfile(UserEntity userEntity) {
    return PersonalProfile.builder()
        .fullname(Fullname.from(new UserFirstname(userEntity.getFirstname()), new UserLastname(userEntity.getLastname())))
        .email(new UserEmail(userEntity.getEmail()))
        .username(new Username(userEntity.getUsername()))
        .userPublicId(new UserPublicId(userEntity.getPublicId()))
        .imageUrl(new UserImageUrl(userEntity.getImageURL()))
        .lastModifiedDate(userEntity.getLastModifiedDate())
        .createdDate(userEntity.getCreatedDate())
        .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
        .dbId(userEntity.getId())
        .build();
  }

  public static PublicProfile toPublicProfile(UserEntity userEntity) {
    return PublicProfile.builder()
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

  public static UserEntity from(User user) {
    var userEntityBuilder = UserEntity.builder();

    if (user.getImageUrl() != null) {
      userEntityBuilder.imageURL(user.getImageUrl().value());
    }

    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    // if (user.getUserAddress() != null) {
    // userEntityBuilder.addressCity(user.getUserAddress().city());
    // userEntityBuilder.addressCountry(user.getUserAddress().country());
    // userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
    // userEntityBuilder.addressStreet(user.getUserAddress().street());
    // }

    return userEntityBuilder
        .authorities(AuthorityEntity.from(user.getAuthorities()))
        .email(user.getEmail().value())
        .firstname(user.getFirstname().value())
        .lastname(user.getLastname().value())
        .lastSeen(user.getLastSeen())
        .id(user.getDbId())
        .build();
  }

  @Override
  public Long getId() {
    return id;
  }

}
