package com.loci.loci_backend.core.social.domain.aggregate;

import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.PublicId;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.common.validation.domain.Assert;
import com.loci.loci_backend.core.social.domain.vo.ContactRequestId;
import com.loci.loci_backend.core.social.infrastructure.secondary.entity.FriendRequestStatus;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.jilt.Opt;

import lombok.Data;

@Data

public class ContactRequest {
  private final ContactRequestId id;
  private UserDBId receiverUserId;
  private UserDBId requestUserId;
  private FriendRequestStatus status;
  private PublicId publicId;

  @Builder(style = BuilderStyle.STAGED)
  public ContactRequest(@Opt ContactRequestId id, UserDBId receiverUserId, UserDBId requestUserId, PublicId publicId) {
    this.status = FriendRequestStatus.PENDING;
    this.id = id;
    this.receiverUserId = receiverUserId;
    this.requestUserId = requestUserId;
    this.publicId = publicId;
  }

  public void assertManadatoryField() {
    Assert.notNull("receiverUserId", receiverUserId);
    Assert.notNull("requestUserId", requestUserId);
  }

  public void initManadatoryField() {
    if (this.publicId == null) {
      this.publicId = PublicId.random();
    }
  }

  public static ContactRequest builderRequest(User sender, User receiver) {

    var contact = ContactRequestBuilder.contactRequest()
        .receiverUserId(receiver.getDbId())
        .requestUserId(sender.getDbId())
        .publicId(null)
        .build();
    contact.initManadatoryField();
    return contact;

  }

}
