package com.loci.loci_backend.core.identity.infrastructure.secondary.repository;

import com.loci.loci_backend.core.identity.infrastructure.secondary.entity.UserSettingsEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserSettingRepository extends JpaRepository<UserSettingsEntity, Long> {

}
