package com.loci.loci_backend.core.social.domain.aggregate;

import java.util.List;

import com.loci.loci_backend.core.identity.domain.aggregate.UserSummary;

import org.jilt.Builder;
import org.jilt.BuilderStyle;
import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class ContactRequestList {
  private Page<ContactRequest> contacts;
  private List<UserSummary> userSummaries;
  @Builder(style = BuilderStyle.STAGED)
  public ContactRequestList(Page<ContactRequest> contacts, List<UserSummary> userSummaries) {
    this.contacts = contacts;
    this.userSummaries = userSummaries;
  }


}
