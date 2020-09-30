/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;

public class PasswordTokenRequest
extends TokenRequest {
    @Key
    private String password;
    @Key
    private String username;

    public PasswordTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, String string2, String string3) {
        super(httpTransport, jsonFactory, genericUrl, "password");
        this.setUsername(string2);
        this.setPassword(string3);
    }

    public final String getPassword() {
        return this.password;
    }

    public final String getUsername() {
        return this.username;
    }

    @Override
    public PasswordTokenRequest set(String string2, Object object) {
        return (PasswordTokenRequest)super.set(string2, object);
    }

    @Override
    public PasswordTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        return (PasswordTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    @Override
    public PasswordTokenRequest setGrantType(String string2) {
        return (PasswordTokenRequest)super.setGrantType(string2);
    }

    public PasswordTokenRequest setPassword(String string2) {
        this.password = Preconditions.checkNotNull(string2);
        return this;
    }

    @Override
    public PasswordTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (PasswordTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public PasswordTokenRequest setResponseClass(Class<? extends TokenResponse> class_) {
        return (PasswordTokenRequest)super.setResponseClass(class_);
    }

    @Override
    public PasswordTokenRequest setScopes(Collection<String> collection) {
        return (PasswordTokenRequest)super.setScopes(collection);
    }

    @Override
    public PasswordTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (PasswordTokenRequest)super.setTokenServerUrl(genericUrl);
    }

    public PasswordTokenRequest setUsername(String string2) {
        this.username = Preconditions.checkNotNull(string2);
        return this;
    }
}

