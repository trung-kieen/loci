package com.loci.loci_backend.core.social.infrastructure.secondary.entity;

import java.util.UUID;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;

import org.jilt.Builder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "contact_request")
@Getter
@Setter
@NoArgsConstructor
public class ContactRequestEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactRequestSequenceGenerator")
  @SequenceGenerator(name = "contactRequestSequenceGenerator", sequenceName = "contact_request_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // readonly entity
  @JoinColumn(name = "receiver_user_id", referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity receiver;

  @Column(name = "receiver_user_id", nullable = false, updatable = false)
  private Long receiverUserId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // readonly entity
  @JoinColumn(name = "request_user_id", nullable = false, referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity requester;

  @Column(name = "request_user_id", nullable = false, updatable = false)
  private Long requestUserId;

  @Enumerated(EnumType.STRING)
  private FriendRequestStatus status;

  @Column(name = "public_id", unique = true)
  private UUID publicId;


  @Builder
  public ContactRequestEntity(Long receiverUserId, Long requestUserId) {
    this.receiverUserId = receiverUserId;
    this.requestUserId = requestUserId;
    this.status = FriendRequestStatus.ofDefault();
  }

  @Override
  public Long getId() {
    return id;
  }

}
