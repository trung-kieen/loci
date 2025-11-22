package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import com.loci.loci_backend.common.user.infrastructure.secondary.enitty.AuthorityEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaAuthorityRepository extends JpaRepository<AuthorityEntity, String> {

}
