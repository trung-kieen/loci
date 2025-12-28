package com.loci.loci_backend.core.notification.infrastructure.secondary.entity;

import java.time.Instant;
import java.util.UUID;

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
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationSequenceGenerator")
  @SequenceGenerator(name = "notificationSequenceGenerator", sequenceName = "notification_sequence", allocationSize = 1)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  @Getter(AccessLevel.NONE)
  @Setter(AccessLevel.NONE)
  private UserEntity user;

  @Column(name = "content", nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column(name = "message_thumbnail", length = 500)
  private String messageThumbnail;

  @Column(name = "read_at")
  private Instant readAt;

  @Column(name = "public_id", unique = true)
  private UUID publicId;

  // public NotificationJpaEntity(UserJpaEntity user, String content) {
  // this.notificationId = UUID.randomUUID();
  // this.user = user;
  // this.content = content;
  // }

  public void markAsRead() {
    this.readAt = Instant.now();
  }

  public boolean isRead() {
    return readAt != null;
  }

  @Override
  public Long getId() {
    return id;
  }
}
