/*
 * Copyright 2026 trung-kieen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    String url = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.VQIsvREn5F2yJD_6EN2WRwHaHa%3Fpid%3DApi&f=1&ipt=47e7757126d0ddcbf716ebd0218d403ae12e008465b6129843d16d5db01edad5&ipo=images";

    // String url =
    // String.format("https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=%d",
    // id);
    return new GroupImageUrl(url);
  }

  @Override
  public void validate() {
    Assert.notBlank("group image url", value);
  }

}
