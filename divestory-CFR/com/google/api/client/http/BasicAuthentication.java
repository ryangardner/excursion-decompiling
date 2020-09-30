/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public final class BasicAuthentication
implements HttpRequestInitializer,
HttpExecuteInterceptor {
    private final String password;
    private final String username;

    public BasicAuthentication(String string2, String string3) {
        this.username = Preconditions.checkNotNull(string2);
        this.password = Preconditions.checkNotNull(string3);
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public void initialize(HttpRequest httpRequest) throws IOException {
        httpRequest.setInterceptor(this);
    }

    @Override
    public void intercept(HttpRequest httpRequest) throws IOException {
        httpRequest.getHeaders().setBasicAuthentication(this.username, this.password);
    }
}

