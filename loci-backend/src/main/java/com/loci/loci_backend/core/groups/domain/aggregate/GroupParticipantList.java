package com.loci.loci_backend.core.groups.domain.aggregate;

import java.util.List;
import java.util.Map;

import com.loci.loci_backend.common.collection.Maps;
import com.loci.loci_backend.common.user.domain.aggregate.User;
import com.loci.loci_backend.common.user.domain.vo.UserDBId;
import com.loci.loci_backend.core.conversation.domain.aggregate.Participant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@Getter
public class GroupParticipantList {
  private List<GroupParticipant> participants;

  public static GroupParticipantList buildFromList(List<Participant> participants, List<User> userDataOfParticipants) {
    Map<UserDBId, User> userDataLookup = Maps.toLookupMap(userDataOfParticipants, User::getDbId);
    List<GroupParticipant> groupParticipants = participants.stream().map(p -> extractGroupParticipant(p, userDataLookup)).toList();

    return new GroupParticipantList(groupParticipants);
  }

  private static GroupParticipant extractGroupParticipant(Participant p, Map<UserDBId, User> userDataLookup) {
    User user = userDataLookup.get(p.getUserId());
    return GroupParticipant.forUserParticipantGroup(user, p);
  }

}
