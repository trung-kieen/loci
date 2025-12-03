package com.loci.loci_backend.core.social.infrastructure.secondary.entity;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactSequenceGenerator")
  @SequenceGenerator(name = "contactSequenceGenerator", sequenceName = "contact_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
  private UserEntity owningUser; // The user who "owns" this contact

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "contact_user_id", nullable = false, referencedColumnName = "id")
  private UserEntity contactUser; // The actual contact person

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blocked_by", referencedColumnName = "id")
  private UserEntity blockedBy; // Who blocked this contact (null if not blocked)

  @Override
  public Long getId() {
    return id;
  }



}
