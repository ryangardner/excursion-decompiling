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
import java.util.Collection;

public class ClientCredentialsTokenRequest
extends TokenRequest {
    public ClientCredentialsTokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl) {
        super(httpTransport, jsonFactory, genericUrl, "client_credentials");
    }

    @Override
    public ClientCredentialsTokenRequest set(String string2, Object object) {
        return (ClientCredentialsTokenRequest)super.set(string2, object);
    }

    @Override
    public ClientCredentialsTokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        return (ClientCredentialsTokenRequest)super.setClientAuthentication(httpExecuteInterceptor);
    }

    @Override
    public ClientCredentialsTokenRequest setGrantType(String string2) {
        return (ClientCredentialsTokenRequest)super.setGrantType(string2);
    }

    @Override
    public ClientCredentialsTokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        return (ClientCredentialsTokenRequest)super.setRequestInitializer(httpRequestInitializer);
    }

    @Override
    public ClientCredentialsTokenRequest setResponseClass(Class<? extends TokenResponse> class_) {
        return (ClientCredentialsTokenRequest)super.setResponseClass(class_);
    }

    @Override
    public ClientCredentialsTokenRequest setScopes(Collection<String> collection) {
        return (ClientCredentialsTokenRequest)super.setScopes(collection);
    }

    @Override
    public ClientCredentialsTokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        return (ClientCredentialsTokenRequest)super.setTokenServerUrl(genericUrl);
    }
}

