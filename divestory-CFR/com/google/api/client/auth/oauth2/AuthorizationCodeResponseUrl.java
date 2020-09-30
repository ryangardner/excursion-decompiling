/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class AuthorizationCodeResponseUrl
extends GenericUrl {
    @Key
    private String code;
    @Key
    private String error;
    @Key(value="error_description")
    private String errorDescription;
    @Key(value="error_uri")
    private String errorUri;
    @Key
    private String state;

    public AuthorizationCodeResponseUrl(String string2) {
        super(string2);
        string2 = this.code;
        boolean bl = true;
        boolean bl2 = string2 == null;
        boolean bl3 = this.error == null;
        if (bl2 == bl3) {
            bl = false;
        }
        Preconditions.checkArgument(bl);
    }

    @Override
    public AuthorizationCodeResponseUrl clone() {
        return (AuthorizationCodeResponseUrl)super.clone();
    }

    public final String getCode() {
        return this.code;
    }

    public final String getError() {
        return this.error;
    }

    public final String getErrorDescription() {
        return this.errorDescription;
    }

    public final String getErrorUri() {
        return this.errorUri;
    }

    public final String getState() {
        return this.state;
    }

    @Override
    public AuthorizationCodeResponseUrl set(String string2, Object object) {
        return (AuthorizationCodeResponseUrl)super.set(string2, object);
    }

    public AuthorizationCodeResponseUrl setCode(String string2) {
        this.code = string2;
        return this;
    }

    public AuthorizationCodeResponseUrl setError(String string2) {
        this.error = string2;
        return this;
    }

    public AuthorizationCodeResponseUrl setErrorDescription(String string2) {
        this.errorDescription = string2;
        return this;
    }

    public AuthorizationCodeResponseUrl setErrorUri(String string2) {
        this.errorUri = string2;
        return this;
    }

    public AuthorizationCodeResponseUrl setState(String string2) {
        this.state = string2;
        return this;
    }
}

