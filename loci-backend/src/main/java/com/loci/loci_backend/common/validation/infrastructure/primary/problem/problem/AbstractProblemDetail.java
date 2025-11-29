package com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "title", "status", "detail", "instance" })
public abstract class AbstractProblemDetail {
  private final String type;
  private final String title;
  private final int status;
  private final String detail;
  private final String instance;

  protected AbstractProblemDetail(String type, String title, int status,
      String detail, String instance) {
    this.type = type;
    this.title = title;
    this.status = status;
    this.detail = detail;
    this.instance = instance;
  }

  // Getters for standard fields
  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public int getStatus() {
    return status;
  }

  public String getDetail() {
    return detail;
  }

  public String getInstance() {
    return instance;
  }
}
