/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.CredentialRefreshListener;
import com.google.api.client.auth.oauth2.CredentialStore;
import com.google.api.client.auth.oauth2.CredentialStoreRefreshListener;
import com.google.api.client.auth.oauth2.DataStoreCredentialRefreshListener;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Lists;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class AuthorizationCodeFlow {
    private final String authorizationServerEncodedUrl;
    private final HttpExecuteInterceptor clientAuthentication;
    private final String clientId;
    private final Clock clock;
    private final CredentialCreatedListener credentialCreatedListener;
    private final DataStore<StoredCredential> credentialDataStore;
    @Deprecated
    private final CredentialStore credentialStore;
    private final JsonFactory jsonFactory;
    private final Credential.AccessMethod method;
    private final Collection<CredentialRefreshListener> refreshListeners;
    private final HttpRequestInitializer requestInitializer;
    private final Collection<String> scopes;
    private final String tokenServerEncodedUrl;
    private final HttpTransport transport;

    protected AuthorizationCodeFlow(Builder builder) {
        this.method = Preconditions.checkNotNull(builder.method);
        this.transport = Preconditions.checkNotNull(builder.transport);
        this.jsonFactory = Preconditions.checkNotNull(builder.jsonFactory);
        this.tokenServerEncodedUrl = Preconditions.checkNotNull(builder.tokenServerUrl).build();
        this.clientAuthentication = builder.clientAuthentication;
        this.clientId = Preconditions.checkNotNull(builder.clientId);
        this.authorizationServerEncodedUrl = Preconditions.checkNotNull(builder.authorizationServerEncodedUrl);
        this.requestInitializer = builder.requestInitializer;
        this.credentialStore = builder.credentialStore;
        this.credentialDataStore = builder.credentialDataStore;
        this.scopes = Collections.unmodifiableCollection(builder.scopes);
        this.clock = Preconditions.checkNotNull(builder.clock);
        this.credentialCreatedListener = builder.credentialCreatedListener;
        this.refreshListeners = Collections.unmodifiableCollection(builder.refreshListeners);
    }

    public AuthorizationCodeFlow(Credential.AccessMethod accessMethod, HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, HttpExecuteInterceptor httpExecuteInterceptor, String string2, String string3) {
        this(new Builder(accessMethod, httpTransport, jsonFactory, genericUrl, httpExecuteInterceptor, string2, string3));
    }

    private Credential newCredential(String string2) {
        Credential.Builder builder = new Credential.Builder(this.method).setTransport(this.transport).setJsonFactory(this.jsonFactory).setTokenServerEncodedUrl(this.tokenServerEncodedUrl).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setClock(this.clock);
        if (this.credentialDataStore != null) {
            builder.addRefreshListener(new DataStoreCredentialRefreshListener(string2, this.credentialDataStore));
        } else if (this.credentialStore != null) {
            builder.addRefreshListener(new CredentialStoreRefreshListener(string2, this.credentialStore));
        }
        builder.getRefreshListeners().addAll(this.refreshListeners);
        return builder.build();
    }

    public Credential createAndStoreCredential(TokenResponse tokenResponse, String object) throws IOException {
        Credential credential = this.newCredential((String)object).setFromTokenResponse(tokenResponse);
        Object object2 = this.credentialStore;
        if (object2 != null) {
            object2.store((String)object, credential);
        }
        if ((object2 = this.credentialDataStore) != null) {
            object2.set((String)object, new StoredCredential(credential));
        }
        if ((object = this.credentialCreatedListener) == null) return credential;
        object.onCredentialCreated(credential, tokenResponse);
        return credential;
    }

    public final String getAuthorizationServerEncodedUrl() {
        return this.authorizationServerEncodedUrl;
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final Clock getClock() {
        return this.clock;
    }

    public final DataStore<StoredCredential> getCredentialDataStore() {
        return this.credentialDataStore;
    }

    @Deprecated
    public final CredentialStore getCredentialStore() {
        return this.credentialStore;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final Credential.AccessMethod getMethod() {
        return this.method;
    }

    public final Collection<CredentialRefreshListener> getRefreshListeners() {
        return this.refreshListeners;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final Collection<String> getScopes() {
        return this.scopes;
    }

    public final String getScopesAsString() {
        return Joiner.on(' ').join(this.scopes);
    }

    public final String getTokenServerEncodedUrl() {
        return this.tokenServerEncodedUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    public Credential loadCredential(String object) throws IOException {
        if (Strings.isNullOrEmpty((String)object)) {
            return null;
        }
        if (this.credentialDataStore == null && this.credentialStore == null) {
            return null;
        }
        Credential credential = this.newCredential((String)object);
        DataStore<StoredCredential> dataStore = this.credentialDataStore;
        if (dataStore == null) {
            if (this.credentialStore.load((String)object, credential)) return credential;
            return null;
        }
        if ((object = dataStore.get((String)object)) == null) {
            return null;
        }
        credential.setAccessToken(((StoredCredential)object).getAccessToken());
        credential.setRefreshToken(((StoredCredential)object).getRefreshToken());
        credential.setExpirationTimeMilliseconds(((StoredCredential)object).getExpirationTimeMilliseconds());
        return credential;
    }

    public AuthorizationCodeRequestUrl newAuthorizationUrl() {
        return new AuthorizationCodeRequestUrl(this.authorizationServerEncodedUrl, this.clientId).setScopes((Collection)this.scopes);
    }

    public AuthorizationCodeTokenRequest newTokenRequest(String string2) {
        return new AuthorizationCodeTokenRequest(this.transport, this.jsonFactory, new GenericUrl(this.tokenServerEncodedUrl), string2).setClientAuthentication(this.clientAuthentication).setRequestInitializer(this.requestInitializer).setScopes((Collection)this.scopes);
    }

    public static class Builder {
        String authorizationServerEncodedUrl;
        HttpExecuteInterceptor clientAuthentication;
        String clientId;
        Clock clock = Clock.SYSTEM;
        CredentialCreatedListener credentialCreatedListener;
        DataStore<StoredCredential> credentialDataStore;
        @Deprecated
        CredentialStore credentialStore;
        JsonFactory jsonFactory;
        Credential.AccessMethod method;
        Collection<CredentialRefreshListener> refreshListeners = Lists.newArrayList();
        HttpRequestInitializer requestInitializer;
        Collection<String> scopes = Lists.newArrayList();
        GenericUrl tokenServerUrl;
        HttpTransport transport;

        public Builder(Credential.AccessMethod accessMethod, HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, HttpExecuteInterceptor httpExecuteInterceptor, String string2, String string3) {
            this.setMethod(accessMethod);
            this.setTransport(httpTransport);
            this.setJsonFactory(jsonFactory);
            this.setTokenServerUrl(genericUrl);
            this.setClientAuthentication(httpExecuteInterceptor);
            this.setClientId(string2);
            this.setAuthorizationServerEncodedUrl(string3);
        }

        public Builder addRefreshListener(CredentialRefreshListener credentialRefreshListener) {
            this.refreshListeners.add(Preconditions.checkNotNull(credentialRefreshListener));
            return this;
        }

        public AuthorizationCodeFlow build() {
            return new AuthorizationCodeFlow(this);
        }

        public final String getAuthorizationServerEncodedUrl() {
            return this.authorizationServerEncodedUrl;
        }

        public final HttpExecuteInterceptor getClientAuthentication() {
            return this.clientAuthentication;
        }

        public final String getClientId() {
            return this.clientId;
        }

        public final Clock getClock() {
            return this.clock;
        }

        public final CredentialCreatedListener getCredentialCreatedListener() {
            return this.credentialCreatedListener;
        }

        public final DataStore<StoredCredential> getCredentialDataStore() {
            return this.credentialDataStore;
        }

        @Deprecated
        public final CredentialStore getCredentialStore() {
            return this.credentialStore;
        }

        public final JsonFactory getJsonFactory() {
            return this.jsonFactory;
        }

        public final Credential.AccessMethod getMethod() {
            return this.method;
        }

        public final Collection<CredentialRefreshListener> getRefreshListeners() {
            return this.refreshListeners;
        }

        public final HttpRequestInitializer getRequestInitializer() {
            return this.requestInitializer;
        }

        public final Collection<String> getScopes() {
            return this.scopes;
        }

        public final GenericUrl getTokenServerUrl() {
            return this.tokenServerUrl;
        }

        public final HttpTransport getTransport() {
            return this.transport;
        }

        public Builder setAuthorizationServerEncodedUrl(String string2) {
            this.authorizationServerEncodedUrl = Preconditions.checkNotNull(string2);
            return this;
        }

        public Builder setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
            this.clientAuthentication = httpExecuteInterceptor;
            return this;
        }

        public Builder setClientId(String string2) {
            this.clientId = Preconditions.checkNotNull(string2);
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setCredentialCreatedListener(CredentialCreatedListener credentialCreatedListener) {
            this.credentialCreatedListener = credentialCreatedListener;
            return this;
        }

        public Builder setCredentialDataStore(DataStore<StoredCredential> dataStore) {
            boolean bl = this.credentialStore == null;
            Preconditions.checkArgument(bl);
            this.credentialDataStore = dataStore;
            return this;
        }

        @Deprecated
        public Builder setCredentialStore(CredentialStore credentialStore) {
            boolean bl = this.credentialDataStore == null;
            Preconditions.checkArgument(bl);
            this.credentialStore = credentialStore;
            return this;
        }

        public Builder setDataStoreFactory(DataStoreFactory dataStoreFactory) throws IOException {
            return this.setCredentialDataStore(StoredCredential.getDefaultDataStore(dataStoreFactory));
        }

        public Builder setJsonFactory(JsonFactory jsonFactory) {
            this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
            return this;
        }

        public Builder setMethod(Credential.AccessMethod accessMethod) {
            this.method = Preconditions.checkNotNull(accessMethod);
            return this;
        }

        public Builder setRefreshListeners(Collection<CredentialRefreshListener> collection) {
            this.refreshListeners = Preconditions.checkNotNull(collection);
            return this;
        }

        public Builder setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
            this.requestInitializer = httpRequestInitializer;
            return this;
        }

        public Builder setScopes(Collection<String> collection) {
            this.scopes = Preconditions.checkNotNull(collection);
            return this;
        }

        public Builder setTokenServerUrl(GenericUrl genericUrl) {
            this.tokenServerUrl = Preconditions.checkNotNull(genericUrl);
            return this;
        }

        public Builder setTransport(HttpTransport httpTransport) {
            this.transport = Preconditions.checkNotNull(httpTransport);
            return this;
        }
    }

    public static interface CredentialCreatedListener {
        public void onCredentialCreated(Credential var1, TokenResponse var2) throws IOException;
    }

}

