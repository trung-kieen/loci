package com.loci.loci_backend.core.identity.infrastructure.secondary.entity;

import com.loci.loci_backend.common.jpa.AbstractAuditingEntity;
import com.loci.loci_backend.common.user.infrastructure.secondary.entity.UserEntity;
import com.loci.loci_backend.core.identity.domain.vo.FriendRequestSettingEnum;
import com.loci.loci_backend.core.identity.domain.vo.LastSeenSettingEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@NoArgsConstructor
public class UserSettingsEntity extends AbstractAuditingEntity<Long> {

  @Id
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId // This makes id = user.id
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(name = "last_seen_setting")
  @Enumerated(EnumType.STRING)
  private LastSeenSettingEnum lastSeenSetting = LastSeenSettingEnum.EVERYONE;

  @Column(name = "friend_request_setting")
  @Enumerated(EnumType.STRING)
  private FriendRequestSettingEnum friendRequestSetting = FriendRequestSettingEnum.EVERYONE;

  @Column(name = "profile_visibility")
  private Boolean profileVisibility = true;


  public UserSettingsEntity(UserEntity user) {
    this.user = user;
    this.id = user.getId();
  }
}
