package com.loci.loci_backend.core.identity.domain.aggregate;

import com.loci.loci_backend.common.mapper.ValueObject;
import com.loci.loci_backend.common.user.domain.vo.UserFirstname;
import com.loci.loci_backend.common.user.domain.vo.UserLastname;

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

  public UserFullname(UserFullname otherVO) {
    this.firstname = otherVO.getFirstname();
    this.lastname = otherVO.getLastname();

  }

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
