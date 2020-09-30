/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import java.io.IOException;
import java.util.Collection;

public class GoogleRefreshTokenRequest
extends RefreshTokenRequest {
    public GoogleRefreshTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, String string4) {
        super(httpTransport, jsonFactory, new GenericUrl("https://oauth2.googleapis.com/token"), string2);
        this.setClientAuthentication(new ClientParametersAuthentication(string3, string4));
    }

    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }

    @Override
    public GoogleRefreshTokenRequest set(String string2, Object object) {
        return (GoogleRefreshTokenRequest)super.set(string2, object);
    }

    @Override
    public GoogleRefreshTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        return (GoogleRefreshTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    @Override
    public GoogleRefreshTokenRequest setGrantType(String string2) {
        return (GoogleRefreshTokenRequest)super.setGrantType(string2);
    }

    @Override
    public GoogleRefreshTokenRequest setRefreshToken(String string2) {
        return (GoogleRefreshTokenRequest)super.setRefreshToken(string2);
    }

    @Override
    public GoogleRefreshTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (GoogleRefreshTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public GoogleRefreshTokenRequest setScopes(Collection<String> collection) {
        return (GoogleRefreshTokenRequest)super.setScopes((Collection)collection);
    }

    @Override
    public GoogleRefreshTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (GoogleRefreshTokenRequest)super.setTokenServerUrl(genericUrl);
    }
}

