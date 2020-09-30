/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Joiner;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Iterator;

public class AuthorizationRequestUrl
extends GenericUrl {
    @Key(value="client_id")
    private String clientId;
    @Key(value="redirect_uri")
    private String redirectUri;
    @Key(value="response_type")
    private String responseTypes;
    @Key(value="scope")
    private String scopes;
    @Key
    private String state;

    public AuthorizationRequestUrl(String string2, String string3, Collection<String> collection) {
        super(string2);
        boolean bl = this.getFragment() == null;
        Preconditions.checkArgument(bl);
        this.setClientId(string3);
        this.setResponseTypes(collection);
    }

    @Override
    public AuthorizationRequestUrl clone() {
        return (AuthorizationRequestUrl)super.clone();
    }

    public final String getClientId() {
        return this.clientId;
    }

    public final String getRedirectUri() {
        return this.redirectUri;
    }

    public final String getResponseTypes() {
        return this.responseTypes;
    }

    public final String getScopes() {
        return this.scopes;
    }

    public final String getState() {
        return this.state;
    }

    @Override
    public AuthorizationRequestUrl set(String string2, Object object) {
        return (AuthorizationRequestUrl)super.set(string2, object);
    }

    public AuthorizationRequestUrl setClientId(String string2) {
        this.clientId = Preconditions.checkNotNull(string2);
        return this;
    }

    public AuthorizationRequestUrl setRedirectUri(String string2) {
        this.redirectUri = string2;
        return this;
    }

    public AuthorizationRequestUrl setResponseTypes(Collection<String> collection) {
        this.responseTypes = Joiner.on(' ').join(collection);
        return this;
    }

    public AuthorizationRequestUrl setScopes(Collection<String> object) {
        object = object != null && object.iterator().hasNext() ? Joiner.on(' ').join((Iterable<?>)object) : null;
        this.scopes = object;
        return this;
    }

    public AuthorizationRequestUrl setState(String string2) {
        this.state = string2;
        return this;
    }
}

