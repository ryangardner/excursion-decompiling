/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;

public class TokenResponse
extends GenericJson {
    @Key(value="access_token")
    private String accessToken;
    @Key(value="expires_in")
    private Long expiresInSeconds;
    @Key(value="refresh_token")
    private String refreshToken;
    @Key
    private String scope;
    @Key(value="token_type")
    private String tokenType;

    @Override
    public TokenResponse clone() {
        return (TokenResponse)super.clone();
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Long getExpiresInSeconds() {
        return this.expiresInSeconds;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public String getScope() {
        return this.scope;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    @Override
    public TokenResponse set(String string2, Object object) {
        return (TokenResponse)super.set(string2, object);
    }

    public TokenResponse setAccessToken(String string2) {
        this.accessToken = Preconditions.checkNotNull(string2);
        return this;
    }

    public TokenResponse setExpiresInSeconds(Long l) {
        this.expiresInSeconds = l;
        return this;
    }

    public TokenResponse setRefreshToken(String string2) {
        this.refreshToken = string2;
        return this;
    }

    public TokenResponse setScope(String string2) {
        this.scope = string2;
        return this;
    }

    public TokenResponse setTokenType(String string2) {
        this.tokenType = Preconditions.checkNotNull(string2);
        return this;
    }
}

