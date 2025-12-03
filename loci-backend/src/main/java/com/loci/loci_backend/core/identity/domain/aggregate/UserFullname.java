package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;
import com.loci.loci_backend.common.util.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFullname implements ValueObject<String> {
  private UserFirstname firstname;
  private UserLastname lastname;

  public static UserFullname from(UserFirstname firstname, UserLastname lastname) {
    return UserFullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }

  public static UserFullname from(UserLastname lastname, UserFirstname firstname) {
    return UserFullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }

  public String value() {
    return firstname.value() + ' ' + lastname.value();
  }

}
