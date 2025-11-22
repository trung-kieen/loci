package com.loci.loci_backend.core.user.domain.profile.aggregate;

import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

import org.apache.catalina.util.ToStringUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fullname {
  private UserFirstname firstname;
  private UserLastname lastname;

  public static Fullname from(UserFirstname firstname, UserLastname lastname) {
    return Fullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }
  public static Fullname from(UserLastname lastname, UserFirstname firstname) {
    return Fullname.builder()
        .firstname(firstname)
        .lastname(lastname)
        .build();
  }

  public String value() {
    return firstname.value() + ' ' + lastname.value();
  }


}
