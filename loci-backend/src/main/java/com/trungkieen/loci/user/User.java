package com.trungkieen.loci.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trungkieen.loci.chat.Chat;
import com.trungkieen.loci.common.BaseAuditingEntity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
// @NamedQuery(name = UserConstants.FIND_USER_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email = :email")
// @NamedQuery(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF, query = "SELECT u FROM User u WHERE u.id != :publicId")
// @NamedQuery(name = UserConstants.FIND_USER_BY_PUBLIC_ID, query = "SELECT u FROM User u WHERE u.id = :publicId")
public class User extends BaseAuditingEntity {
  private static final int LAST_ACTIVE_INTERNAL = 5;

  @Id

  private String  id;
  private String firstname;
  private String lastname;
  private String email;
  private LocalDateTime lastSeen;

  @OneToMany(mappedBy = "sender")
  private List<Chat> chatAsSender;

  @OneToMany(mappedBy = "recipient")
  private List<Chat> chatAsRecipient;

  @Transient
  public boolean isUserOnline() {
    return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(LAST_ACTIVE_INTERNAL));
  }



}
