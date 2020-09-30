/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis;

import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import java.io.IOException;

public final class MethodOverride
implements HttpExecuteInterceptor,
HttpRequestInitializer {
    public static final String HEADER = "X-HTTP-Method-Override";
    static final int MAX_URL_LENGTH = 2048;
    private final boolean overrideAllMethods;

    public MethodOverride() {
        this(false);
    }

    MethodOverride(boolean bl) {
        this.overrideAllMethods = bl;
    }

    private boolean overrideThisMethod(HttpRequest httpRequest) throws IOException {
        String string2 = httpRequest.getRequestMethod();
        if (string2.equals("POST")) {
            return false;
        }
        if (string2.equals("GET")) {
            if (httpRequest.getUrl().build().length() <= 2048) return httpRequest.getTransport().supportsMethod(string2) ^ true;
            return true;
        }
        if (!this.overrideAllMethods) return httpRequest.getTransport().supportsMethod(string2) ^ true;
        return true;
    }

    @Override
    public void initialize(HttpRequest httpRequest) {
        httpRequest.setInterceptor(this);
    }

    @Override
    public void intercept(HttpRequest httpRequest) throws IOException {
        if (!this.overrideThisMethod(httpRequest)) return;
        String string2 = httpRequest.getRequestMethod();
        httpRequest.setRequestMethod("POST");
        httpRequest.getHeaders().set(HEADER, string2);
        if (string2.equals("GET")) {
            httpRequest.setContent(new UrlEncodedContent(httpRequest.getUrl().clone()));
            httpRequest.getUrl().clear();
            return;
        }
        if (httpRequest.getContent() != null) return;
        httpRequest.setContent(new EmptyContent());
    }

    public static final class Builder {
        private boolean overrideAllMethods;

        public MethodOverride build() {
            return new MethodOverride(this.overrideAllMethods);
        }

        public boolean getOverrideAllMethods() {
            return this.overrideAllMethods;
        }

        public Builder setOverrideAllMethods(boolean bl) {
            this.overrideAllMethods = bl;
            return this;
        }
    }

}

