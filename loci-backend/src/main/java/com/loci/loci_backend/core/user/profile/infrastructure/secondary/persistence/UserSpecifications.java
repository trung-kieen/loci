package com.loci.loci_backend.core.user.profile.infrastructure.secondary.persistence;

import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import jakarta.persistence.criteria.Predicate;

public class UserSpecifications {

  // TODO: Change logic follow the username
  // public static Specification<UserEntity> searchActiveUsers(UserSearchCriteria
  // criteria) {
  // return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
  // {
  // Predicate activePredicate = cb.isTrue(root.get("active"));
  //
  // String keyword = criteria.getKeyword();
  // if (keyword == null || keyword.isBlank()) {
  // return activePredicate;
  // }
  //
  // String lowerKeyword = "%" + keyword.toLowerCase() + "%";
  // Predicate namePredicate = cb.like(cb.lower(root.get("name")), lowerKeyword);
  // Predicate emailPredicate = cb.like(cb.lower(root.get("email")),
  // lowerKeyword);
  //
  // return cb.and(activePredicate, cb.or(namePredicate, emailPredicate));
  // };
  // }

  public static Specification<UserEntity> searchActiveUsers(String keyword) {
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

      String searchKeyword = keyword;
      if (searchKeyword == null || searchKeyword.isBlank()) {
        searchKeyword = "";
      }
      String lowerKeyword = "%" + searchKeyword.toLowerCase() + "%";
      Predicate emailPredicate = cb.like(cb.lower(root.get("email")), lowerKeyword);

      return emailPredicate;
    };
  }

}
