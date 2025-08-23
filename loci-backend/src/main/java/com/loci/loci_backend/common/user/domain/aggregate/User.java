package com.loci.loci_backend.common.user.domain.aggregate;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
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

import lombok.Builder;
import lombok.Data;

@Builder
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

  public User(UserLastname lastname, UserFirstname firstname, UserEmail email, UserPublicId userPublicId,
      UserImageUrl imageUrl, Instant lastModifiedDate, Instant createdDate, Set<Authority> authorities, Long dbId,
      UserAddress userAddress, Instant lastSeen) {
    this.lastname = lastname;
    this.firstname = firstname;
    this.email = email;
    this.userPublicId = userPublicId;
    this.imageUrl = imageUrl;
    this.lastModifiedDate = lastModifiedDate;
    this.createdDate = createdDate;
    this.authorities = authorities;
    this.dbId = dbId;
    this.userAddress = userAddress;
    this.lastSeen = lastSeen;
  }

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
    }
  }

  public void initFieldForSignup() {
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }


  public static User fromTokenAttributes(Map<String, Object> attributes, Collection<String> rolesFromAccessToken) {

    // TODO: Check the old sync method attribute and new attribute name

    // String firstname =
    // String.valueOf(token.getTokenAttributes().get("given_name"));
    // String lastname =
    // String.valueOf(token.getTokenAttributes().get("family_name"));
    // String email = String.valueOf(token.getTokenAttributes().get("email"));
    // UserEntity.Gender gender = token.getTokenAttributes().get("gender") == null ?
    // null :
    // UserEntity.Gender.valueOf(String.valueOf(token.getTokenAttributes().get("gender")).toUpperCase());

    // UserEntity user = UserEntity.builder()
    // .firstname(firstname)
    // .lastname(lastname)
    // .email(email)
    // .gender(gender)
    // .build();

    UserBuilder userBuilder = User.builder();

    if (attributes.containsKey("preferred_email")) {
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if (attributes.containsKey("last_name")) {
      userBuilder.lastname(new UserLastname(attributes.get("last_name").toString()));
    }

    if (attributes.containsKey("first_name")) {
      userBuilder.firstname(new UserFirstname(attributes.get("first_name").toString()));
    }

    if (attributes.containsKey("picture")) {
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if (attributes.containsKey("last_signed_in")) {
      userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
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
