/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class TokenErrorResponse
extends GenericJson {
    @Key
    private String error;
    @Key(value="error_description")
    private String errorDescription;
    @Key(value="error_uri")
    private String errorUri;

    @Override
    public TokenErrorResponse clone() {
        return (TokenErrorResponse)super.clone();
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

    @Override
    public TokenErrorResponse set(String string2, Object object) {
        return (TokenErrorResponse)super.set(string2, object);
    }

    public TokenErrorResponse setError(String string2) {
        this.error = Preconditions.checkNotNull(string2);
        return this;
    }

    public TokenErrorResponse setErrorDescription(String string2) {
        this.errorDescription = string2;
        return this;
    }

    public TokenErrorResponse setErrorUri(String string2) {
        this.errorUri = string2;
        return this;
    }
}

