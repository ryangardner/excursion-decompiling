/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

public class MockGoogleJsonClient
extends AbstractGoogleJsonClient {
    protected MockGoogleJsonClient(Builder builder) {
        super(builder);
    }

    public MockGoogleJsonClient(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, HttpRequestInitializer httpRequestInitializer, boolean bl) {
        this(new Builder(httpTransport, jsonFactory, string2, string3, httpRequestInitializer, bl));
    }

    public static class Builder
    extends AbstractGoogleJsonClient.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, HttpRequestInitializer httpRequestInitializer, boolean bl) {
            super(httpTransport, jsonFactory, string2, string3, httpRequestInitializer, bl);
        }

        @Override
        public MockGoogleJsonClient build() {
            return new MockGoogleJsonClient(this);
        }

        @Override
        public Builder setApplicationName(String string2) {
            return (Builder)super.setApplicationName(string2);
        }

        @Override
        public Builder setGoogleClientRequestInitializer(GoogleClientRequestInitializer googleClientRequestInitializer) {
            return (Builder)super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
        }

        @Override
        public Builder setHttpRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder)super.setHttpRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setRootUrl(String string2) {
            return (Builder)super.setRootUrl(string2);
        }

        @Override
        public Builder setServicePath(String string2) {
            return (Builder)super.setServicePath(string2);
        }

        @Override
        public Builder setSuppressAllChecks(boolean bl) {
            return (Builder)super.setSuppressAllChecks(bl);
        }

        @Override
        public Builder setSuppressPatternChecks(boolean bl) {
            return (Builder)super.setSuppressPatternChecks(bl);
        }

        @Override
        public Builder setSuppressRequiredParameterChecks(boolean bl) {
            return (Builder)super.setSuppressRequiredParameterChecks(bl);
        }
    }

}

