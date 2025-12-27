package com.loci.loci_backend.common.mapper;

import com.loci.loci_backend.common.util.NullSafe;
import com.loci.loci_backend.core.identity.domain.aggregate.UserFullname;

import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ValueObjectTypeConverter {

  // For enum
  // public <R, T extends Enum<T>> T wrap(Class<R> class1, T enumValue) {
  // return enumValue;
  // }

  public UserFullname wrap(@TargetType Class<UserFullname> clazz, UserFullname
  value) {
  return value;
  }

  // public FriendRequestStatus wrap(@TargetType Class<FriendRequestStatus> clazz,
  // FriendRequestStatus value) {
  // return value;
  // }


  // Not mark class as ValueObject<T> to avoid conflict
  public <T extends Enum<T>> T wrap(@TargetType Class<T> clazz, T value) {
    return value;
  }

  public <R, T extends ValueObject<R>> T wrap(@TargetType Class<T> clazz, R value) {
    // if (clazz.isEnum() && value instanceof String) {
    // return (T) Enum.valueOf((Class<Enum>) clazz, (String) value);
    // }
    return NullSafe.constructOrNull(clazz, value);
  }

  public <R> R unwrap(ValueObject<R> value) {
    return NullSafe.getIfPresent(value);
  }

}
