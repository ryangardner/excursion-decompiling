/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.AuthorizationRequestUrl;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import java.util.Collection;
import java.util.Collections;

public class AuthorizationCodeRequestUrl
extends AuthorizationRequestUrl {
    public AuthorizationCodeRequestUrl(String string2, String string3) {
        super(string2, string3, Collections.singleton("code"));
    }

    @Override
    public AuthorizationCodeRequestUrl clone() {
        return (AuthorizationCodeRequestUrl)super.clone();
    }

    @Override
    public AuthorizationCodeRequestUrl set(String string2, Object object) {
        return (AuthorizationCodeRequestUrl)super.set(string2, object);
    }

    @Override
    public AuthorizationCodeRequestUrl setClientId(String string2) {
        return (AuthorizationCodeRequestUrl)super.setClientId(string2);
    }

    @Override
    public AuthorizationCodeRequestUrl setRedirectUri(String string2) {
        return (AuthorizationCodeRequestUrl)super.setRedirectUri(string2);
    }

    @Override
    public AuthorizationCodeRequestUrl setResponseTypes(Collection<String> collection) {
        return (AuthorizationCodeRequestUrl)super.setResponseTypes(collection);
    }

    @Override
    public AuthorizationCodeRequestUrl setScopes(Collection<String> collection) {
        return (AuthorizationCodeRequestUrl)super.setScopes(collection);
    }

    @Override
    public AuthorizationCodeRequestUrl setState(String string2) {
        return (AuthorizationCodeRequestUrl)super.setState(string2);
    }
}

