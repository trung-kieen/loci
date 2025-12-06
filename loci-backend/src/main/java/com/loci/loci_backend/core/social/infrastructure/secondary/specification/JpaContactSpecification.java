package com.loci.loci_backend.core.social.infrastructure.secondary.specification;

import com.loci.loci_backend.core.social.infrastructure.secondary.entity.ContactEntity;

import org.springframework.data.jpa.domain.Specification;

public class JpaContactSpecification {

  public static Specification<ContactEntity> searchContact(Long userId0, Long userId1) {
    return (root, query, cb) -> {
      if (userId0 == null || userId1== null) {
        return cb.disjunction();
      }

      return cb.or(
          cb.and(
              cb.equal(root.get("owningUser").get("id"), userId0),
              cb.equal(root.get("contactUser").get("id"), userId1)),
          cb.and(
              cb.equal(root.get("owningUser").get("id"), userId1),
              cb.equal(root.get("contactUser").get("id"), userId0)));
    };
  }

}
