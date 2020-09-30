/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
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
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class GoogleAuthorizationCodeTokenRequest
extends AuthorizationCodeTokenRequest {
    public GoogleAuthorizationCodeTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, String string4, String string5) {
        this(httpTransport, jsonFactory, "https://oauth2.googleapis.com/token", string2, string3, string4, string5);
    }

    public GoogleAuthorizationCodeTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, String string2, String string3, String string4, String string5, String string6) {
        super(httpTransport, jsonFactory, new GenericUrl(string2), string5);
        this.setClientAuthentication(new ClientParametersAuthentication(string3, string4));
        this.setRedirectUri(string6);
    }

    @Override
    public GoogleTokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(GoogleTokenResponse.class);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest set(String string2, Object object) {
        return (GoogleAuthorizationCodeTokenRequest)super.set(string2, object);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        Preconditions.checkNotNull(httpExecuteInterceptor);
        return (GoogleAuthorizationCodeTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setCode(String string2) {
        return (GoogleAuthorizationCodeTokenRequest)super.setCode(string2);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setGrantType(String string2) {
        return (GoogleAuthorizationCodeTokenRequest)super.setGrantType(string2);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setRedirectUri(String string2) {
        Preconditions.checkNotNull(string2);
        return (GoogleAuthorizationCodeTokenRequest)super.setRedirectUri(string2);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (GoogleAuthorizationCodeTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setScopes(Collection<String> collection) {
        return (GoogleAuthorizationCodeTokenRequest)super.setScopes((Collection)collection);
    }

    @Override
    public GoogleAuthorizationCodeTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (GoogleAuthorizationCodeTokenRequest)super.setTokenServerUrl(genericUrl);
    }
}

