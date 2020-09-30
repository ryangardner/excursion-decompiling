/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.compute;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.OAuth2Utils;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.Clock;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class ComputeCredential
extends Credential {
    public static final String TOKEN_SERVER_ENCODED_URL;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OAuth2Utils.getMetadataServerUrl());
        stringBuilder.append("/computeMetadata/v1/instance/service-accounts/default/token");
        TOKEN_SERVER_ENCODED_URL = stringBuilder.toString();
    }

    protected ComputeCredential(Builder builder) {
        super(builder);
    }

    public ComputeCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
        this(new Builder(httpTransport, jsonFactory));
    }

    @Override
    protected TokenResponse executeRefreshToken() throws IOException {
        Object object = new GenericUrl(this.getTokenServerEncodedUrl());
        object = this.getTransport().createRequestFactory().buildGetRequest((GenericUrl)object);
        ((HttpRequest)object).setParser(new JsonObjectParser(this.getJsonFactory()));
        ((HttpRequest)object).getHeaders().set("Metadata-Flavor", "Google");
        return ((HttpRequest)object).execute().parseAs(TokenResponse.class);
    }

    public static class Builder
    extends Credential.Builder {
        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory) {
            super(BearerToken.authorizationHeaderAccessMethod());
            this.setTransport(httpTransport);
            this.setJsonFactory(jsonFactory);
            this.setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL);
        }

        @Override
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder)super.addRefreshListener(credentialRefreshListener);
        }

        @Override
        public ComputeCredential build() {
            return new ComputeCredential(this);
        }

        @Override
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            boolean bl = httpExecuteInterceptor == null;
            Preconditions.checkArgument(bl);
            return this;
        }

        @Override
        public Builder setClock(Clock clock) {
            return (Builder)super.setClock(clock);
        }

        @Override
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(Preconditions.checkNotNull(jsonFactory));
        }

        @Override
        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            return (Builder)super.setRefreshListeners(collection);
        }

        @Override
        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            return (Builder)super.setRequestInitializer(httpRequestInitializer);
        }

        @Override
        public Builder setTokenServerEncodedUrl(String string2) {
            return (Builder)super.setTokenServerEncodedUrl(Preconditions.checkNotNull(string2));
        }

        @Override
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder)super.setTokenServerUrl(Preconditions.checkNotNull(genericUrl));
        }

        @Override
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder)super.setTransport(Preconditions.checkNotNull(httpTransport));
        }
    }

}

