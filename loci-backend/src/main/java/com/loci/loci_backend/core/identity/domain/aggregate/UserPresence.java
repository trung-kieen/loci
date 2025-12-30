package com.loci.loci_backend.core.identity.domain.aggregate;

import java.time.Instant;

import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.identity.domain.vo.PresenceStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.jilt.Opt;

import lombok.Data;

@Data
public class UserPresence {
  private PublicId publicId;
  private final UserDBId userId;
  private PresenceStatus status;
  private Instant lastSeen;
  // private Duration ttl;

  @Builder(style = BuilderStyle.STAGED)
  public UserPresence(UserDBId userId, @Opt PublicId publicId, PresenceStatus status) {
    this.publicId = publicId;
    this.userId = userId;
    this.status = status;
  }

  public static UserPresence offline(UserDBId userId, PublicId publicId) {
    return UserPresenceBuilder.userPresence()
        .userId(userId)
        .status(PresenceStatus.offline())
        .publicId(publicId)
        .build();
  }

  public static UserPresence offline(UserDBId userId) {
    return UserPresenceBuilder.userPresence()
        .userId(userId)
        .status(PresenceStatus.offline())
        .build();
  }

  public boolean isOnline() {
    return this.status.isConnected();
  }

  public void goOnline() {

  }

  public void goOffline() {
    // TODO: listen to websocket connection event if need of high consistency
  }

}
