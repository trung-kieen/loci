package com.loci.loci_backend.core.groups.domain.vo;

import java.util.Random;

import com.loci.loci_backend.common.ddd.domain.contract.ValueObject;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.common.validation.domain.Validatable;

public record GroupImageUrl(String value) implements ValueObject<String>, Validatable {


  public static GroupImageUrl random() {
    // return randomPhotorealistic();
    return randomCartoonStyle();
  }

  public static GroupImageUrl randomCartoonStyle() {
    // String url = "https://avatar.iran.liara.run/public";

    Random random = new Random();
    int id = random.nextInt(1000);
    String url = String.format("https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=%d", id);
    return new GroupImageUrl(url);
  }

  @Override
  public void validate() {
    Assert.notBlank("group image url", value);
  }

}
