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

public class RefreshTokenRequest
extends TokenRequest {
    @Key(value="refresh_token")
    private String refreshToken;

    public RefreshTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, String string2) {
        super(httpTransport, jsonFactory, genericUrl, "refresh_token");
        this.setRefreshToken(string2);
    }

    public final String getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public RefreshTokenRequest set(String string2, Object object) {
        return (RefreshTokenRequest)super.set(string2, object);
    }

    @Override
    public RefreshTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        return (RefreshTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    @Override
    public RefreshTokenRequest setGrantType(String string2) {
        return (RefreshTokenRequest)super.setGrantType(string2);
    }

    public RefreshTokenRequest setRefreshToken(String string2) {
        this.refreshToken = Preconditions.checkNotNull(string2);
        return this;
    }

    @Override
    public RefreshTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (RefreshTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public RefreshTokenRequest setResponseClass(Class<? extends TokenResponse> class_) {
        return (RefreshTokenRequest)super.setResponseClass(class_);
    }

    @Override
    public RefreshTokenRequest setScopes(Collection<String> collection) {
        return (RefreshTokenRequest)super.setScopes(collection);
    }

    @Override
    public RefreshTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (RefreshTokenRequest)super.setTokenServerUrl(genericUrl);
    }
}

