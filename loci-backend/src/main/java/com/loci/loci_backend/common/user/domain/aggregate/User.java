package com.loci.loci_backend.common.user.domain.aggregate;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.domain.vo.UserAddress;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.user.profile.domain.aggregate.PrivacySetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class User {

  private UserLastname lastname;

  private UserFirstname firstname;

  private UserEmail email;

  private UserPublicId userPublicId;

  private UserImageUrl imageUrl;

  private Instant lastModifiedDate;

  private Instant createdDate;

  private Set<Authority> authorities;

  private Long dbId;

  private UserAddress userAddress;

  private Instant lastSeen;

  private PrivacySetting privacySetting;


  private void assertMandatoryFields() {
    Assert.notNull("lastname", lastname);
    Assert.notNull("firstname", firstname);
    Assert.notNull("email", email);
    Assert.notNull("authorities", authorities);
  }

  public void updateFromUser(User user) {
    if (user != null) {
      this.email = user.email;
      this.imageUrl = user.imageUrl;
      this.firstname = user.firstname;
      this.lastname = user.lastname;
      this.firstname = user.firstname;
      this.lastname = user.lastname;
      this.userPublicId = user.userPublicId;
    }
  }

  public void provideMandatoryField() {
    if (this.userPublicId == null) {
      this.initFieldForSignup();
    }

  }

  public void initFieldForSignup() {
    this.userPublicId = UserPublicId.random();
  }

  public static User fromTokenAttributes(Map<String, Object> attributes, Collection<String> rolesFromAccessToken) {


    UserBuilder userBuilder = User.builder();

    if (attributes.containsKey("email")) {
      userBuilder.email(new UserEmail(attributes.get("email").toString()));
    }

    if (attributes.containsKey("given_name")) {
      userBuilder.lastname(new UserLastname(attributes.get("given_name").toString()));
    }

    if (attributes.containsKey("family_name")) {
      userBuilder.firstname(new UserFirstname(attributes.get("family_name").toString()));
    }

    if (attributes.containsKey("picture")) {
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if (attributes.containsKey("last_signed_in")) {
      userBuilder.lastSeen(
          Instant.parse(attributes.get("last_signed_in").toString()));
    } else {
      userBuilder.lastSeen(Instant.now());
    }

    Set<Authority> authorities = rolesFromAccessToken.stream()
        .map(authority -> Authority.builder()
            .name(new AuthorityName(authority))
            .build())
        .collect(Collectors.toSet());

    userBuilder.authorities(authorities);

    return userBuilder.build();
  }

  public Username getUsername() {
    return new Username(email.value());
  }
}
