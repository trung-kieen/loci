package com.loci.loci_backend.core.social.domain.service;

public interface FriendManager {
  void addFriend();

  void unFriend();

  void acceptFriendRequest();

  void rejectFriendRequest();

  void viewListContactRequest();

}
