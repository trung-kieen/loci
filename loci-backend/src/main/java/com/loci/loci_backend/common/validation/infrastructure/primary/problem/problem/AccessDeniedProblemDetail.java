package com.loci.loci_backend.common.validation.infrastructure.primary.problem.problem;

import java.time.Instant;
import java.util.List;

public class AccessDeniedProblemDetail extends AbstractProblemDetail {
    private final String traceId;
    private final String requestId;
    private final String timestamp;
    private final String errorCode;
    private final String user;
    private final List<String> authorities;
    private final String requiredAuthority;

    private AccessDeniedProblemDetail(Builder builder) {
        super(
            "/problems/forbidden", // Use the docs website domain as prefix
            "Access Denied",
            403,
            builder.detail,
            builder.instance
        );
        this.traceId = builder.traceId;
        this.requestId = builder.requestId;
        this.timestamp = builder.timestamp;
        this.errorCode = builder.errorCode;
        this.user = builder.user;
        this.authorities = builder.authorities;
        this.requiredAuthority = builder.requiredAuthority;
    }

    // Getters for extension fields
    public String getTraceId() { return traceId; }
    public String getRequestId() { return requestId; }
    public String getTimestamp() { return timestamp; }
    public String getErrorCode() { return errorCode; }
    public String getUser() { return user; }
    public List<String> getAuthorities() { return authorities; }
    public String getRequiredAuthority() { return requiredAuthority; }

    public static Builder builder(String instance) {
        return new Builder(instance);
    }

    public static class Builder {
        private String detail = "You don't have permission to access this resource";
        private final String instance;
        private String traceId;
        private String requestId;
        private String timestamp = Instant.now().toString();
        private String errorCode = "AUTH_002";
        private String user = "anonymous";
        private List<String> authorities = List.of();
        private String requiredAuthority;

        private Builder(String instance) {
            this.instance = instance;
        }

        public Builder detail(String detail) { this.detail = detail; return this; }
        public Builder traceId(String traceId) { this.traceId = traceId; return this; }
        public Builder requestId(String requestId) { this.requestId = requestId; return this; }
        public Builder user(String user, List<String> authorities) {
            this.user = user;
            this.authorities = authorities;
            return this;
        }
        public Builder requiredAuthority(String required) { this.requiredAuthority = required; return this; }
        public AccessDeniedProblemDetail build() { return new AccessDeniedProblemDetail(this); }
    }
}
