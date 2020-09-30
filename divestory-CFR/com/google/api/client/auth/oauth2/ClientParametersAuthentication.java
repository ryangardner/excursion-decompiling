/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.util.Data;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class ClientParametersAuthentication
implements HttpRequestInitializer,
HttpExecuteInterceptor {
    private final String clientId;
    private final String clientSecret;

    public ClientParametersAuthentication(String string2, String string3) {
        this.clientId = Preconditions.checkNotNull(string2);
        this.clientSecret = string3;
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
    }

    @Override
    public void intercept(HttpRequest object) throws IOException {
        object = Data.mapOf(UrlEncodedContent.getContent((HttpRequest)object).getData());
        object.put("client_id", this.clientId);
        String string2 = this.clientSecret;
        if (string2 == null) return;
        object.put("client_secret", string2);
    }
}

