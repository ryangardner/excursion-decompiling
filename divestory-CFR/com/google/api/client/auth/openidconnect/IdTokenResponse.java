/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.openidconnect;

import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class IdTokenResponse
extends TokenResponse {
    @Key(value="id_token")
    private String idToken;

    public static IdTokenResponse execute(TokenRequest tokenRequest) throws IOException {
        return tokenRequest.executeUnparsed().parseAs(IdTokenResponse.class);
    }

    @Override
    public IdTokenResponse clone() {
        return (IdTokenResponse)super.clone();
    }

    public final String getIdToken() {
        return this.idToken;
    }

    public IdToken parseIdToken() throws IOException {
        return IdToken.parse(this.getFactory(), this.idToken);
    }

    @Override
    public IdTokenResponse set(String string2, Object object) {
        return (IdTokenResponse)super.set(string2, object);
    }

    @Override
    public IdTokenResponse setAccessToken(String string2) {
        super.setAccessToken(string2);
        return this;
    }

    @Override
    public IdTokenResponse setExpiresInSeconds(Long l) {
        super.setExpiresInSeconds(l);
        return this;
    }

    public IdTokenResponse setIdToken(String string2) {
        this.idToken = Preconditions.checkNotNull(string2);
        return this;
    }

    @Override
    public IdTokenResponse setRefreshToken(String string2) {
        super.setRefreshToken(string2);
        return this;
    }

    @Override
    public IdTokenResponse setScope(String string2) {
        super.setScope(string2);
        return this;
    }

    @Override
    public IdTokenResponse setTokenType(String string2) {
        super.setTokenType(string2);
        return this;
    }
}

