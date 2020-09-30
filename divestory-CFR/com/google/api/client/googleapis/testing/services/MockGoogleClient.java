/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.testing.services;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;

public class MockGoogleClient
extends AbstractGoogleClient {
    protected MockGoogleClient(Builder builder) {
        super(builder);
    }

    public MockGoogleClient(HttpTransport httpTransport, String string2, String string3, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
        this(new Builder(httpTransport, string2, string3, objectParser, httpRequestInitializer));
    }

    public static class Builder
    extends AbstractGoogleClient.Builder {
        public Builder(HttpTransport httpTransport, String string2, String string3, ObjectParser objectParser, HttpRequestInitializer httpRequestInitializer) {
            super(httpTransport, string2, string3, objectParser, httpRequestInitializer);
        }

        @Override
        public MockGoogleClient build() {
            return new MockGoogleClient(this);
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

