/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.services.json;

import com.google.api.client.googleapis.services.AbstractGoogleClient;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.ObjectParser;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public abstract class AbstractGoogleJsonClient
extends AbstractGoogleClient {
    protected AbstractGoogleJsonClient(Builder builder) {
        super(builder);
    }

    public final JsonFactory getJsonFactory() {
        return this.getObjectParser().getJsonFactory();
    }

    @Override
    public JsonObjectParser getObjectParser() {
        return (JsonObjectParser)super.getObjectParser();
    }

    public static abstract class Builder
    extends AbstractGoogleClient.Builder {
        protected Builder(HttpTransport httpTransport, JsonFactory collection, String string2, String string3, HttpRequestInitializer httpRequestInitializer, boolean bl) {
            JsonObjectParser.Builder builder = new JsonObjectParser.Builder((JsonFactory)((Object)collection));
            collection = bl ? Arrays.asList("data", "error") : Collections.emptySet();
            super(httpTransport, string2, string3, builder.setWrapperKeys(collection).build(), httpRequestInitializer);
        }

        @Override
        public abstract AbstractGoogleJsonClient build();

        public final JsonFactory getJsonFactory() {
            return this.getObjectParser().getJsonFactory();
        }

        @Override
        public final JsonObjectParser getObjectParser() {
            return (JsonObjectParser)super.getObjectParser();
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

