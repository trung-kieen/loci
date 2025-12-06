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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
// TODO: unique constrant of pair of user and contact_user
public class ContactEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactSequenceGenerator")
  @SequenceGenerator(name = "contactSequenceGenerator", sequenceName = "contact_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity owningUser; // The user who "owns" this contact

  @Column(name = "user_id", nullable = false, updatable = false)
  private Long owningUserId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "contact_user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity contactUser; // The actual contact person

  @Column(name = "contact_user_id", nullable = false, updatable = false)
  private Long contactUserId;

  @ManyToOne(fetch = FetchType.LAZY)
  // readonly field just for enforce reference key
  @JoinColumn(name = "blocked_by", referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity blockedBy; // Who blocked this contact (null if not blocked)

  @Column(name = "blocked_by", nullable = false, updatable = false)
  private Long blockedByUserId;

  // TODO: status
  @Override
  public Long getId() {
    return id;
  }

}
