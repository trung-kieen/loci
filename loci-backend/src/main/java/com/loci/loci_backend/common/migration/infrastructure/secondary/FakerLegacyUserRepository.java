package com.loci.loci_backend.common.migration.infrastructure.secondary;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javafaker.Faker;
import com.loci.loci_backend.common.authentication.domain.Username;
import com.loci.loci_backend.common.ddd.infrastructure.stereotype.SecondaryPort;
import com.loci.loci_backend.common.migration.domain.repository.LegacyUserRepository;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.aggregate.UserBuilder;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.PublicId;

import org.springframework.stereotype.Service;

@SecondaryPort
public class FakerLegacyUserRepository implements LegacyUserRepository {

  private final Faker faker = new Faker();

  @Override
  public List<User> fetchUsers(int limit) {
    return IntStream.range(0, limit)
        .mapToObj(i -> fakeUser())
        .collect(Collectors.toList());
  }

  private User fakeUser() {
    Authority userAuthority = Authority.builder().name(new AuthorityName("user")).build();
    Set<Authority> userAuthorities = Set.of(userAuthority);
    return UserBuilder.user()
        .userPublicId(PublicId.random())
        .dbId(null)
        .email(new UserEmail(faker.internet().emailAddress()))
        .firstname(new UserFirstname(faker.name().firstName()))
        .lastname(new UserLastname(faker.name().lastName()))
        .username(new Username(faker.name().username()))
        .profilePicture(UserImageUrl.random())
        .createdDate(Instant.now())
        .lastModifiedDate(Instant.now())
        .bio(null)
        .lastActive(Instant.now())
        .authorities(userAuthorities)
        // .userAddress(new UserAddress(street, city, zipCode, country))
        // .passwordHash(faker.internet().password())
        // .enabled(true)
        .build();
  }

}
