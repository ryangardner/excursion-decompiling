/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class GoogleTokenResponse
extends TokenResponse {
    @Key(value="id_token")
    private String idToken;

    @Override
    public GoogleTokenResponse clone() {
        return (GoogleTokenResponse)super.clone();
    }

    public final String getIdToken() {
        return this.idToken;
    }

    public GoogleIdToken parseIdToken() throws IOException {
        return GoogleIdToken.parse(this.getFactory(), this.getIdToken());
    }

    @Override
    public GoogleTokenResponse set(String string2, Object object) {
        return (GoogleTokenResponse)super.set(string2, object);
    }

    @Override
    public GoogleTokenResponse setAccessToken(String string2) {
        return (GoogleTokenResponse)super.setAccessToken(string2);
    }

    @Override
    public GoogleTokenResponse setExpiresInSeconds(Long l) {
        return (GoogleTokenResponse)super.setExpiresInSeconds(l);
    }

    public GoogleTokenResponse setIdToken(String string2) {
        this.idToken = Preconditions.checkNotNull(string2);
        return this;
    }

    @Override
    public GoogleTokenResponse setRefreshToken(String string2) {
        return (GoogleTokenResponse)super.setRefreshToken(string2);
    }

    @Override
    public GoogleTokenResponse setScope(String string2) {
        return (GoogleTokenResponse)super.setScope(string2);
    }

    @Override
    public GoogleTokenResponse setTokenType(String string2) {
        return (GoogleTokenResponse)super.setTokenType(string2);
    }
}

