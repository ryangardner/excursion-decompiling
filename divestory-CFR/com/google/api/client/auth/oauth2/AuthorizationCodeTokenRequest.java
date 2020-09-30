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

public class AuthorizationCodeTokenRequest
extends TokenRequest {
    @Key
    private String code;
    @Key(value="redirect_uri")
    private String redirectUri;

    public AuthorizationCodeTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, String string2) {
        super(httpTransport, jsonFactory, genericUrl, "authorization_code");
        this.setCode(string2);
    }

    public final String getCode() {
        return this.code;
    }

    public final String getRedirectUri() {
        return this.redirectUri;
    }

    @Override
    public AuthorizationCodeTokenRequest set(String string2, Object object) {
        return (AuthorizationCodeTokenRequest)super.set(string2, object);
    }

    @Override
    public AuthorizationCodeTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        return (AuthorizationCodeTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    public AuthorizationCodeTokenRequest setCode(String string2) {
        this.code = Preconditions.checkNotNull(string2);
        return this;
    }

    @Override
    public AuthorizationCodeTokenRequest setGrantType(String string2) {
        return (AuthorizationCodeTokenRequest)super.setGrantType(string2);
    }

    public AuthorizationCodeTokenRequest setRedirectUri(String string2) {
        this.redirectUri = string2;
        return this;
    }

    @Override
    public AuthorizationCodeTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (AuthorizationCodeTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public AuthorizationCodeTokenRequest setResponseClass(Class<? extends TokenResponse> class_) {
        return (AuthorizationCodeTokenRequest)super.setResponseClass(class_);
    }

    @Override
    public AuthorizationCodeTokenRequest setScopes(Collection<String> collection) {
        return (AuthorizationCodeTokenRequest)super.setScopes(collection);
    }

    @Override
    public AuthorizationCodeTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (AuthorizationCodeTokenRequest)super.setTokenServerUrl(genericUrl);
    }
}

