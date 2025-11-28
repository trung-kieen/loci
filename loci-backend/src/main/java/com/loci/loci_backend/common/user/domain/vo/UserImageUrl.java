package com.loci.loci_backend.common.user.domain.vo;

import java.util.Optional;
import java.util.Random;

import com.github.javafaker.service.RandomService;
import com.loci.loci_backend.common.validation.domain.Assert;

import io.micrometer.common.util.StringUtils;

/**
 * UserImageUrl
 */
public record UserImageUrl(String value) {
  public UserImageUrl {
    Assert.field("imageUrl", value).maxLength(1000);
  }

  public String valueOrDefault() {
    if (this.value == null) {
      return UserImageUrl.random().value();
    }
    return this.value;
  }

  public static Optional<UserImageUrl> of(String url) {
    return Optional.ofNullable(url).filter(StringUtils::isNotBlank).map(UserImageUrl::new);
  }

  public static UserImageUrl randomPhotorealistic() {
    Random random = new Random();
    String gender = random.nextBoolean() ? "male" : "female";
    int size = 256; // 64, 128, 256, or 512
    String url = generatePhotorealistic(gender, size);
    System.out.println(url);
    return new UserImageUrl(url);
  }

  public static UserImageUrl random(){
    return randomPhotorealistic();
    // return randomCartoonStyle();
  }
  public static UserImageUrl randomCartoonStyle() {
    String url = "https://avatar.iran.liara.run/public";
    return new UserImageUrl(url);
  }

  private static String generatePhotorealistic(String gender, int size) {
    Random random = new Random();
    // Uses reliable AI-generated portraits from faker-js assets
    return String.format(
        "https://cdn.jsdelivr.net/gh/faker-js/assets-person-portrait/%s/%d/%d.jpg",
        gender, size, random.nextInt(100));
  }

}
