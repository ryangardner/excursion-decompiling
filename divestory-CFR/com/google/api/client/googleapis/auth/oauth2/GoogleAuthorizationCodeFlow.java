/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;

public class GoogleAuthorizationCodeFlow
extends AuthorizationCodeFlow {
    private final String accessType;
    private final String approvalPrompt;

    protected GoogleAuthorizationCodeFlow(Builder builder) {
        super(builder);
        this.accessType = builder.accessType;
        this.approvalPrompt = builder.approvalPrompt;
    }

    public GoogleAuthorizationCodeFlow(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, Collection<String> collection) {
        this(new Builder(httpTransport, jsonFactory, string2, string3, collection));
    }

    public final String getAccessType() {
        return this.accessType;
    }

    public final String getApprovalPrompt() {
        return this.approvalPrompt;
    }

    @Override
    public GoogleAuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new GoogleAuthorizationCodeRequestUrl(this.getAuthorizationServerEncodedUrl(), this.getClientId(), "", this.getScopes()).setAccessType(this.accessType).setApprovalPrompt(this.approvalPrompt);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest newTokenRequest(String string2) {
        return ((GoogleAuthorizationCodeTokenRequest)((GoogleAuthorizationCodeTokenRequest)new GoogleAuthorizationCodeTokenRequest(this.getTransport(), this.getJsonFactory(), this.getTokenServerEncodedUrl(), "", "", string2, "").setClientAuthentication(this.getClientAuthentication())).setRequestInitializer(this.getRequestInitializer())).setScopes((Collection)this.getScopes());
    }

    public static class Builder
    extends AuthorizationCodeFlow.Builder {
        String accessType;
        String approvalPrompt;

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, GoogleClientSecrets googleClientSecrets, Collection<String> collection) {
            super(BearerToken.authorizationHeaderAccessMethod(), httpTransport, jsonFactory, new GenericUrl("https://oauth2.googleapis.com/token"), new ClientParametersAuthentication(googleClientSecrets.getDetails().getClientId(), googleClientSecrets.getDetails().getClientSecret()), googleClientSecrets.getDetails().getClientId(), "https://accounts.google.com/o/oauth2/auth");
            this.setScopes((Collection)collection);
        }

        public Builder(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, Collection<String> collection) {
            super(BearerToken.authorizationHeaderAccessMethod(), httpTransport, jsonFactory, new GenericUrl("https://oauth2.googleapis.com/token"), new ClientParametersAuthentication(string2, string3), string2, "https://accounts.google.com/o/oauth2/auth");
            this.setScopes((Collection)collection);
        }

        @Override
        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            return (Builder)super.addRefreshListener(credentialRefreshListener);
        }

        @Override
        public GoogleAuthorizationCodeFlow build() {
            return new GoogleAuthorizationCodeFlow(this);
        }

        public final String getAccessType() {
            return this.accessType;
        }

        public final String getApprovalPrompt() {
            return this.approvalPrompt;
        }

        public Builder setAccessType(String string2) {
            this.accessType = string2;
            return this;
        }

        public Builder setApprovalPrompt(String string2) {
            this.approvalPrompt = string2;
            return this;
        }

        @Override
        public Builder setAuthorizationServerEncodedUrl(String string2) {
            return (Builder)super.setAuthorizationServerEncodedUrl(string2);
        }

        @Override
        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            return (Builder)super.setClientAuthentication(httpExecuteInterceptor);
        }

        @Override
        public Builder setClientId(String string2) {
            return (Builder)super.setClientId(string2);
        }

        @Override
        public Builder setClock(Clock clock) {
            return (Builder)super.setClock(clock);
        }

        @Override
        public Builder setCredentialCreatedListener(AuthorizationCodeFlow.CredentialCreatedListener credentialCreatedListener) {
            return (Builder)super.setCredentialCreatedListener(credentialCreatedListener);
        }

        @Override
        public Builder setCredentialDataStore(DataStore<StoredCredential> dataStore) {
            return (Builder)super.setCredentialDataStore(dataStore);
        }

        @Deprecated
        @Override
        public Builder setCredentialStore(CredentialStore credentialStore) {
            return (Builder)super.setCredentialStore(credentialStore);
        }

        @Override
        public Builder setDataStoreFactory(DataStoreFactory dataStoreFactory) throws IOException {
            return (Builder)super.setDataStoreFactory(dataStoreFactory);
        }

        @Override
        public Builder setJsonFactory(JsonFactory jsonFactory) {
            return (Builder)super.setJsonFactory(jsonFactory);
        }

        @Override
        public Builder setMethod(Credential.AccessMethod accessMethod) {
            return (Builder)super.setMethod(accessMethod);
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
        public Builder setScopes(Collection<String> collection) {
            Preconditions.checkState(collection.isEmpty() ^ true);
            return (Builder)super.setScopes(collection);
        }

        @Override
        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            return (Builder)super.setTokenServerUrl(genericUrl);
        }

        @Override
        public Builder setTransport(HttpTransport httpTransport) {
            return (Builder)super.setTransport(httpTransport);
        }
    }

}

