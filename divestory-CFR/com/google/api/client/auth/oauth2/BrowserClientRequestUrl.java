/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import java.util.Collection;
import java.util.Collections;

public class BrowserClientRequestUrl
extends AuthorizationRequestUrl {
    public BrowserClientRequestUrl(String string2, String string3) {
        super(string2, string3, Collections.singleton("token"));
    }

    @Override
    public BrowserClientRequestUrl clone() {
        return (BrowserClientRequestUrl)super.clone();
    }

    @Override
    public BrowserClientRequestUrl set(String string2, Object object) {
        return (BrowserClientRequestUrl)super.set(string2, object);
    }

    @Override
    public BrowserClientRequestUrl setClientId(String string2) {
        return (BrowserClientRequestUrl)super.setClientId(string2);
    }

    @Override
    public BrowserClientRequestUrl setRedirectUri(String string2) {
        return (BrowserClientRequestUrl)super.setRedirectUri(string2);
    }

    @Override
    public BrowserClientRequestUrl setResponseTypes(Collection<String> collection) {
        return (BrowserClientRequestUrl)super.setResponseTypes(collection);
    }

    @Override
    public BrowserClientRequestUrl setScopes(Collection<String> collection) {
        return (BrowserClientRequestUrl)super.setScopes(collection);
    }

    @Override
    public BrowserClientRequestUrl setState(String string2) {
        return (BrowserClientRequestUrl)super.setState(string2);
    }
}

