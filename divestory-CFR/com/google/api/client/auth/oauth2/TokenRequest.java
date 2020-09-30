/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import java.io.IOException;
import java.util.Collection;

public class TokenRequest
extends GenericData {
    HttpExecuteInterceptor clientAuthentication;
    @Key(value="grant_type")
    private String grantType;
    private final JsonFactory jsonFactory;
    HttpRequestInitializer requestInitializer;
    protected Class<? extends TokenResponse> responseClass;
    @Key(value="scope")
    private String scopes;
    private GenericUrl tokenServerUrl;
    private final HttpTransport transport;

    public TokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, String string2) {
        this(httpTransport, jsonFactory, genericUrl, string2, TokenResponse.class);
    }

    public TokenRequest(HttpTransport httpTransport, JsonFactory jsonFactory, GenericUrl genericUrl, String string2, Class<? extends TokenResponse> class_) {
        this.transport = Preconditions.checkNotNull(httpTransport);
        this.jsonFactory = Preconditions.checkNotNull(jsonFactory);
        this.setTokenServerUrl(genericUrl);
        this.setGrantType(string2);
        this.setResponseClass(class_);
    }

    public TokenResponse execute() throws IOException {
        return this.executeUnparsed().parseAs(this.responseClass);
    }

    public final HttpResponse executeUnparsed() throws IOException {
        Object object = this.transport.createRequestFactory(new HttpRequestInitializer(){

            @Override
            public void initialize(HttpRequest httpRequest) throws IOException {
                if (TokenRequest.this.requestInitializer != null) {
                    TokenRequest.this.requestInitializer.initialize(httpRequest);
                }
                httpRequest.setInterceptor(new HttpExecuteInterceptor(httpRequest.getInterceptor()){
                    final /* synthetic */ HttpExecuteInterceptor val$interceptor;
                    {
                        this.val$interceptor = httpExecuteInterceptor;
                    }

                    @Override
                    public void intercept(HttpRequest httpRequest) throws IOException {
                        HttpExecuteInterceptor httpExecuteInterceptor = this.val$interceptor;
                        if (httpExecuteInterceptor != null) {
                            httpExecuteInterceptor.intercept(httpRequest);
                        }
                        if (TokenRequest.this.clientAuthentication == null) return;
                        TokenRequest.this.clientAuthentication.intercept(httpRequest);
                    }
                });
            }

        }).buildPostRequest(this.tokenServerUrl, new UrlEncodedContent(this));
        ((HttpRequest)object).setParser(new JsonObjectParser(this.jsonFactory));
        ((HttpRequest)object).setThrowExceptionOnExecuteError(false);
        object = ((HttpRequest)object).execute();
        if (!((HttpResponse)object).isSuccessStatusCode()) throw TokenResponseException.from(this.jsonFactory, (HttpResponse)object);
        return object;
    }

    public final HttpExecuteInterceptor getClientAuthentication() {
        return this.clientAuthentication;
    }

    public final String getGrantType() {
        return this.grantType;
    }

    public final JsonFactory getJsonFactory() {
        return this.jsonFactory;
    }

    public final HttpRequestInitializer getRequestInitializer() {
        return this.requestInitializer;
    }

    public final Class<? extends TokenResponse> getResponseClass() {
        return this.responseClass;
    }

    public final String getScopes() {
        return this.scopes;
    }

    public final GenericUrl getTokenServerUrl() {
        return this.tokenServerUrl;
    }

    public final HttpTransport getTransport() {
        return this.transport;
    }

    @Override
    public TokenRequest set(String string2, Object object) {
        return (TokenRequest)super.set(string2, object);
    }

    public TokenRequest setClientAuthentication(HttpExecuteInterceptor httpExecuteInterceptor) {
        this.clientAuthentication = httpExecuteInterceptor;
        return this;
    }

    public TokenRequest setGrantType(String string2) {
        this.grantType = Preconditions.checkNotNull(string2);
        return this;
    }

    public TokenRequest setRequestInitializer(HttpRequestInitializer httpRequestInitializer) {
        this.requestInitializer = httpRequestInitializer;
        return this;
    }

    public TokenRequest setResponseClass(Class<? extends TokenResponse> class_) {
        this.responseClass = class_;
        return this;
    }

    public TokenRequest setScopes(Collection<String> object) {
        object = object == null ? null : Joiner.on(' ').join((Iterable<?>)object);
        this.scopes = object;
        return this;
    }

    public TokenRequest setTokenServerUrl(GenericUrl genericUrl) {
        this.tokenServerUrl = genericUrl;
        boolean bl = genericUrl.getFragment() == null;
        Preconditions.checkArgument(bl);
        return this;
    }

}

