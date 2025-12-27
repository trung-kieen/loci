package com.loci.loci_backend.core.identity.infrastructure.secondary.specification;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.discovery.domain.vo.SuggestFriendCriteria;
import com.loci.loci_backend.core.discovery.domain.vo.UserSearchCriteria;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class UserSpecifications {
  private UserSpecifications() {

  }

  public static Specification<UserEntity> fromCriteria(SuggestFriendCriteria criteria) {
    return UserSpecifications.excludeUsername(criteria.getCurrentUsername().value());

  }

  public static Specification<UserEntity> fromCriteria(UserSearchCriteria criteria) {
    return UserSpecifications.searchActiveUsers(criteria.getKeyword().value(), criteria.getCurrentUsername().value());
  }

  public static Specification<UserEntity> searchActiveUsers(String searchKeyword, String currentUsername) {
    // TODO: search for username, email, name by elastic search
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      Assert.notNull("keyword", searchKeyword);
      Assert.field("current username", currentUsername).notNull().notBlank();

      String lowerKeyword = "%" + searchKeyword.toLowerCase() + "%";
      Predicate usernamePredicate = cb.like(cb.lower(root.get("username")), lowerKeyword);
      Predicate emailPredicate = cb.like(cb.lower(root.get("email")), lowerKeyword);

      // Exclude current user
      Predicate excludeCurrentUserPredicate = cb.notEqual(root.get("username"), currentUsername);

      // TODO: use elastic search instead
      // Combine
      return cb.and(excludeCurrentUserPredicate, cb.or(usernamePredicate, emailPredicate));
    };
  }

  public static Specification<UserEntity> excludeUsername(String currentUsername) {
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

      Assert.field("current username", currentUsername).notNull().notBlank();
      Predicate excludeCurrentUserPredicate = cb.notEqual(root.get("username"), currentUsername);

      // Combine predicates
      return excludeCurrentUserPredicate;
    };
  }
}
