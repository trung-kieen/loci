package com.loci.loci_backend.common.user.infrastructure.secondary.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.loci.loci_backend.common.user.domain.aggregate.Authority;
import com.loci.loci_backend.common.user.domain.repository.AuthorityRepository;
import com.loci.loci_backend.common.user.infrastructure.secondary.enitty.AuthorityEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class SpringDataAuthorityRepository implements AuthorityRepository {
  private final JpaAuthorityRepository repository;

  @Transactional(readOnly = false)
  @Override
  public Set<Authority> saveAll(Collection<Authority> authorities) {
    Set<AuthorityEntity> entities = AuthorityEntity.from(authorities);
    Set<AuthorityEntity> savedEntities = new HashSet<>();
    for (AuthorityEntity authorityEntity : entities) {
      if (!repository.existsById(authorityEntity.getName())) {
        AuthorityEntity newEntity = repository.save(authorityEntity);
        savedEntities.add(newEntity);
      } else {
        repository.findById(authorityEntity.getName()).ifPresent(savedEntities::add);
      }
    }
    repository.flush();
    return AuthorityEntity.toDomain(savedEntities);
  }

  // @Override
  // public Set<Authority> saveAll(Collection<Authority> authorities) {
  // Set<AuthorityEntity> savedEntities = new HashSet<>();
  // for (AuthorityEntity authorityEntity: AuthorityEntity.from(authorities)){
  // if(!repository.existsById(authorityEntity.getName())){
  // AuthorityEntity newEntity = repository.save(authorityEntity);
  // savedEntities.add(newEntity);
  // }
  // }
  // return AuthorityEntity.toDomain(savedEntities);
  //
  // }

  @Transactional(readOnly = false)
  @Override
  public Authority save(Authority authority) {
    return AuthorityEntity.toDomain(repository.save(AuthorityEntity.from(authority)));
  }

  @Transactional(readOnly = false)
  @Override
  public boolean exists(Authority authority) {
    return repository.existsById(AuthorityEntity.from(authority).getName());
  }

}
