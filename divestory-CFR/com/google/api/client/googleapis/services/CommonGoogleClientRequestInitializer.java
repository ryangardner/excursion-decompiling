/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpHeaders;
import java.io.IOException;

public class CommonGoogleClientRequestInitializer
implements GoogleClientRequestInitializer {
    private static final String REQUEST_REASON_HEADER_NAME = "X-Goog-Request-Reason";
    private static final String USER_PROJECT_HEADER_NAME = "X-Goog-User-Project";
    private final String key;
    private final String requestReason;
    private final String userAgent;
    private final String userIp;
    private final String userProject;

    @Deprecated
    public CommonGoogleClientRequestInitializer() {
        this(CommonGoogleClientRequestInitializer.newBuilder());
    }

    protected CommonGoogleClientRequestInitializer(Builder builder) {
        this.key = builder.getKey();
        this.userIp = builder.getUserIp();
        this.userAgent = builder.getUserAgent();
        this.requestReason = builder.getRequestReason();
        this.userProject = builder.getUserProject();
    }

    @Deprecated
    public CommonGoogleClientRequestInitializer(String string2) {
        this(string2, null);
    }

    @Deprecated
    public CommonGoogleClientRequestInitializer(String string2, String string3) {
        this(CommonGoogleClientRequestInitializer.newBuilder().setKey(string2).setUserIp(string3));
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getKey() {
        return this.key;
    }

    public final String getRequestReason() {
        return this.requestReason;
    }

    public final String getUserAgent() {
        return this.userAgent;
    }

    public final String getUserIp() {
        return this.userIp;
    }

    public final String getUserProject() {
        return this.userProject;
    }

    @Override
    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
        String string2 = this.key;
        if (string2 != null) {
            abstractGoogleClientRequest.put("key", (Object)string2);
        }
        if ((string2 = this.userIp) != null) {
            abstractGoogleClientRequest.put("userIp", (Object)string2);
        }
        if (this.userAgent != null) {
            abstractGoogleClientRequest.getRequestHeaders().setUserAgent(this.userAgent);
        }
        if (this.requestReason != null) {
            abstractGoogleClientRequest.getRequestHeaders().set(REQUEST_REASON_HEADER_NAME, this.requestReason);
        }
        if (this.userProject == null) return;
        abstractGoogleClientRequest.getRequestHeaders().set(USER_PROJECT_HEADER_NAME, this.userProject);
    }

    public static class Builder {
        private String key;
        private String requestReason;
        private String userAgent;
        private String userIp;
        private String userProject;

        protected Builder() {
        }

        public CommonGoogleClientRequestInitializer build() {
            return new CommonGoogleClientRequestInitializer(this);
        }

        public String getKey() {
            return this.key;
        }

        public String getRequestReason() {
            return this.requestReason;
        }

        public String getUserAgent() {
            return this.userAgent;
        }

        public String getUserIp() {
            return this.userIp;
        }

        public String getUserProject() {
            return this.userProject;
        }

        protected Builder self() {
            return this;
        }

        public Builder setKey(String string2) {
            this.key = string2;
            return this.self();
        }

        public Builder setRequestReason(String string2) {
            this.requestReason = string2;
            return this.self();
        }

        public Builder setUserAgent(String string2) {
            this.userAgent = string2;
            return this.self();
        }

        public Builder setUserIp(String string2) {
            this.userIp = string2;
            return this.self();
        }

        public Builder setUserProject(String string2) {
            this.userProject = string2;
            return this.self();
        }
    }

}

