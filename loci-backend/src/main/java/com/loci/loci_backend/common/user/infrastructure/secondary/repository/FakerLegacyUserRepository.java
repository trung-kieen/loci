package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.javafaker.Faker;
import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.repository.LegacyUserRepository;
import com.loci.loci_backend.common.user.domain.vo.AuthorityName;
import com.loci.loci_backend.common.user.domain.vo.UserEmail;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserImageUrl;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.user.domain.vo.UserPublicId;
import org.springframework.stereotype.Service;

@Service
public class FakerLegacyUserRepository implements LegacyUserRepository {

  private final Faker faker = new Faker();
  private final Random random = new Random();

  @Override
  public List<User> fetchUsers(int limit) {
    return IntStream.range(0, limit)
        .mapToObj(i -> fakeUser())
        .collect(Collectors.toList());
  }

  private User fakeUser() {
    Authority userAuthority = Authority.builder().name(new AuthorityName("user")).build();
    Set<Authority> userAuthorities = Set.of(userAuthority);
    return User.builder()
        .lastname(new UserLastname(faker.name().lastName()))
        .firstname(new UserFirstname(faker.name().firstName()))
        .email(new UserEmail(faker.internet().emailAddress()))
        .userPublicId(UserPublicId.random())
        .imageUrl(UserImageUrl.random())
        .lastModifiedDate(Instant.now())
        .createdDate(Instant.now())
        .authorities(userAuthorities)
        // .userAddress(new UserAddress(street, city, zipCode, country))
        // .passwordHash(faker.internet().password())
        // .enabled(true)
        .build();
  }

}
