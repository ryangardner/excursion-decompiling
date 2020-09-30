/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.OAuthCredentialsResponse;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedParser;
import java.io.IOException;

public abstract class AbstractOAuthGetToken
extends GenericUrl {
    public String consumerKey;
    public OAuthSigner signer;
    public HttpTransport transport;
    protected boolean usePost;

    protected AbstractOAuthGetToken(String string2) {
        super(string2);
    }

    public OAuthParameters createParameters() {
        OAuthParameters oAuthParameters = new OAuthParameters();
        oAuthParameters.consumerKey = this.consumerKey;
        oAuthParameters.signer = this.signer;
        return oAuthParameters;
    }

    public final OAuthCredentialsResponse execute() throws IOException {
        Object object = this.transport.createRequestFactory();
        Object object2 = this.usePost ? "POST" : "GET";
        object2 = ((HttpRequestFactory)object).buildRequest((String)object2, this, null);
        this.createParameters().intercept((HttpRequest)object2);
        object = ((HttpRequest)object2).execute();
        ((HttpResponse)object).setContentLoggingLimit(0);
        object2 = new OAuthCredentialsResponse();
        UrlEncodedParser.parse(((HttpResponse)object).parseAsString(), object2);
        return object2;
    }
}

