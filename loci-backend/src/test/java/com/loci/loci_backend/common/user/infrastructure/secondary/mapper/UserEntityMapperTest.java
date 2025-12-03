package com.loci.loci_backend.common.user.infrastructure.secondary.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.AuthorityEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntityBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.PersonalProfileBuilder;
import com.loci.loci_backend.core.identity.domain.aggregate.PrivacySetting;
import com.loci.loci_backend.core.identity.domain.aggregate.PublicProfile;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;
import com.loci.loci_backend.core.identity.domain.vo.ProfileBio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserEntityMapper - Unit Tests (written in TDD style)")
class UserEntityMapperTest {

  @Mock
  private AuthorityEntityMapper authorityEntityMapper;

  private UserEntityMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new UserEntityMapper(authorityEntityMapper);
  }

  @Test
  @DisplayName("toPublicProfile() → maps all fields correctly (happy path)")
  void toPublicProfile_mapsCorrectly() {
    // Given
    UUID publicId = UUID.randomUUID();
    Instant now = Instant.now();
    PrivacySetting privacySettings = PrivacySetting.ofDefault();
    UserEntity entity = UserEntityBuilder.userEntity()
        .publicId(publicId)
        .id(10L)
        .email("john@doe.com")
        .firstname("John")
        .lastname("Doe")
        .username("johndoe")
        .profilePicture("https://example.com/avatar.jpg")
        .bio(null)
        .lastActive(now)
        .lastSeenSetting(privacySettings.getLastSeenSetting().value())
        .friendRequestSetting(privacySettings.getFriendRequestSetting().value())
        .profileVisibility(privacySettings.getProfileVisibility().value())
        .authorities(null)
        .build();

    // When
    PublicProfile result = mapper.toPublicProfile(entity);

    // Then
    assertThat(result.getPublicId().value()).isEqualTo(publicId);
    assertThat(result.getEmail().value()).isEqualTo("john@doe.com");
    assertThat(result.getFullname().getFirstname().value()).isEqualTo("John");
    assertThat(result.getFullname().getLastname().value()).isEqualTo("Doe");
    assertThat(result.getUsername().value()).isEqualTo("johndoe");
  }

  @Test
  @DisplayName("from(PersonalProfile) → reverse maps to UserEntity + delegates authorities (happy path)")
  void fromPersonalProfile_mapsCorrectly() {
    // Given
    PrivacySetting privacy = PrivacySetting.ofDefault();
    Set<Authority> domainAuths = Set.of(mock(Authority.class));
    Set<AuthorityEntity> entityAuths = Set.of(mock(AuthorityEntity.class));

    UserPublicId publicId = UserPublicId.random();

    PersonalProfile profile = PersonalProfileBuilder.personalProfile()
        .dbId(99L)
        .email(new UserEmail("new@email.com"))
        .fullname(UserFullname.from(new UserFirstname("New"), new UserLastname("User")))
        .username(new Username("newuser"))
        .imageUrl(new UserImageUrl("https://new.com/pic.jpg"))
        .bio(new ProfileBio("New bio"))
        .createdDate(null)
        .lastModifiedDate(null)
        .lastActive(Instant.now())
        .privacySetting(privacy)
        .authorities(domainAuths)
        .userPublicId(publicId)
        .build();

    when(authorityEntityMapper.from(domainAuths)).thenReturn(entityAuths);

    // When
    UserEntity result = mapper.from(profile);

    // Then
    assertThat(result.getId()).isEqualTo(99L);
    assertThat(result.getPublicId()).isEqualTo(publicId.value());
    assertThat(result.getEmail()).isEqualTo("new@email.com");
    assertThat(result.getFirstname()).isEqualTo("New");
    assertThat(result.getLastname()).isEqualTo("User");
    assertThat(result.getUsername()).isEqualTo("newuser");
    assertThat(result.getProfilePicture()).isEqualTo("https://new.com/pic.jpg");
    assertThat(result.getBio()).isEqualTo("New bio");
    assertThat(result.getLastSeenSetting()).isEqualTo(privacy.getLastSeenSetting().value());
    assertThat(result.getFriendRequestSetting()).isEqualTo(privacy.getFriendRequestSetting().value());
    assertThat(result.getProfileVisibility()).isEqualTo(privacy.getProfileVisibility().value());
    assertThat(result.getAuthorities()).isSameAs(entityAuths);

    verify(authorityEntityMapper).from(domainAuths);
  }

}
