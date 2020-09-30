/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import com.google.api.client.util.Clock;
import java.io.IOException;

public class MockGoogleCredential
extends GoogleCredential {
    public static final String ACCESS_TOKEN = "access_xyz";
    private static final String DEFAULT_TOKEN_RESPONSE_JSON = String.format("{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}", "access_xyz", "3600", "refresh123", "Bearer");
    private static final String EXPIRES_IN_SECONDS = "3600";
    public static final String REFRESH_TOKEN = "refresh123";
    private static final String TOKEN_RESPONSE = "{\"access_token\": \"%s\", \"expires_in\":  %s, \"refresh_token\": \"%s\", \"token_type\": \"%s\"}";
    private static final String TOKEN_TYPE = "Bearer";

    public MockGoogleCredential(Builder builder) {
        super(builder);
    }

    public static MockHttpTransport newMockHttpTransportWithSampleTokenResponse() {
        Object object = new MockLowLevelHttpResponse().setContentType("application/json; charset=UTF-8").setContent(DEFAULT_TOKEN_RESPONSE_JSON);
        object = new MockLowLevelHttpRequest().setResponse((MockLowLevelHttpResponse)object);
        return new MockHttpTransport.Builder().setLowLevelHttpRequest((MockLowLevelHttpRequest)object).build();
    }

    public static class Builder
    extends GoogleCredential.Builder {
        @Override
        public MockGoogleCredential build() {
            if (this.getTransport() == null) {
                this.setTransport(new MockHttpTransport.Builder().build());
            }
            if (this.getClientAuthentication() == null) {
                this.setClientAuthentication(new MockClientAuthentication());
            }
            if (this.getJsonFactory() != null) return new MockGoogleCredential(this);
            this.setJsonFactory(new JacksonFactory());
            return new MockGoogleCredential(this);
        }

        @Override
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            return (Builder)super.setClientAuthentication(httpExecuteInterceptor);
        }

        @Override
        public Builder setClock(Clock clock) {
            return (Builder)super.setClock(clock);
        }

        @Override
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(jsonFactory);
        }

        @Override
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder)super.setTransport(httpTransport);
        }
    }

    private static class MockClientAuthentication
    implements HttpExecuteInterceptor {
        private MockClientAuthentication() {
        }

        @Override
        public void intercept(HttpRequest httpRequest) throws IOException {
        }
    }

}

