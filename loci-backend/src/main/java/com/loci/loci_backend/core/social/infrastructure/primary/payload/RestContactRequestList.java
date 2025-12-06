package com.loci.loci_backend.core.social.infrastructure.primary.payload;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class RestContactRequestList {
  private Page<RestContactRequest> requests;

  public RestContactRequestList(Page<RestContactRequest> requests) {
    this.requests = requests;
  }

}
