package com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem;

import java.time.Instant;

public class AuthenticationProblemDetail extends AbstractProblemDetail {
  private final String traceId;
  private final String requestId;
  private final String timestamp;
  private final String errorCode;

  private AuthenticationProblemDetail(Builder builder) {
    super(
        "/problems/unauthorized", // Use the docs website domain as prefix
        "Authentication Failed",
        401,
        builder.detail,
        builder.instance);
    this.traceId = builder.traceId;
    this.requestId = builder.requestId;
    this.timestamp = builder.timestamp;
    this.errorCode = builder.errorCode;
  }

  // Getters for extension fields
  public String getTraceId() {
    return traceId;
  }

  public String getRequestId() {
    return requestId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public static Builder builder(String detail, String instance) {
    return new Builder(detail, instance);
  }

  public static class Builder {
    private final String detail;
    private final String instance;
    private String traceId;
    private String requestId;
    private String timestamp = Instant.now().toString();
    private String errorCode = "AUTH_001";

    private Builder(String detail, String instance) {
      this.detail = detail;
      this.instance = instance;
    }

    public Builder traceId(String traceId) {
      this.traceId = traceId;
      return this;
    }

    public Builder requestId(String requestId) {
      this.requestId = requestId;
      return this;
    }

    public Builder timestamp(String timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public Builder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public AuthenticationProblemDetail build() {
      return new AuthenticationProblemDetail(this);
    }
  }
}
