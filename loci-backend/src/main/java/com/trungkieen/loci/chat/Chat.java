package com.trungkieen.loci.chat;

import static jakarta.persistence.GenerationType.UUID;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.trungkieen.loci.common.BaseAuditingEntity;
import com.trungkieen.loci.message.Message;
import com.trungkieen.loci.message.MessageState;
import com.trungkieen.loci.message.MessageType;
import com.trungkieen.loci.user.User;

import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
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
@Table(name = "chat")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID, query = "SELECT DISTINCT c FROM Chat c WHERE c.sender.id = :senderId OR c.recipient.id = :senderId ORDER BY createdDate DESC")
@NamedQuery(name = ChatConstants.FIND_CHAT_BY_SENDER_ID_AND_RECEIVER, query = "SELECT DISTINCT c FROM Chat c WHERE (c.sender.id = :senderId AND c.recipient.id = :recipientId) OR (c.sender.id = :recipientId AND c.recipient.id = :senderId) ORDER BY createdDate DESC")
public class Chat extends BaseAuditingEntity {

  @Id
  @GeneratedValue(strategy = UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne
  @JoinColumn(name = "recipient_id")
  private User recipient;

  private String recippent;

  @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
  private List<Message> messages;

  @Transient
  public String getChatName(String senderId) {
    if (recipient.getId().equals(senderId)) {
      return sender.getFirstname() + " " + sender.getLastname();
    }
    return recipient.getFirstname() + " " + recipient.getLastname();
  }

  @Transient
  public String getTargetChatName(String senderId) {
    if (sender.getId().equals(senderId)) {
      return sender.getFirstname() + " " + sender.getLastname();
    }
    return sender.getFirstname() + " " + sender.getLastname();
  }

  @Transient
  public long getUnrreadMessages(String senderId) {
    return this.messages.stream().filter(m -> m.getReceiverId().equals(senderId))
        .filter(m -> m.getState().equals(MessageState.PENDING))
        .count();
  }

  @Transient
  public String getLastMessage() {
    if (messages != null && !messages.isEmpty()) {
      if (messages.get(0).getType() != MessageType.TEXT) {
        return "Attachment";
      }
      return messages.get(0).getContent();
    }
    return null;
  }

  public LocalDateTime getLastMessageTime() {
    if (messages != null && !messages.isEmpty()) {
      return messages.get(0).getCreatedDate();
    }
    return null;
  }
}
