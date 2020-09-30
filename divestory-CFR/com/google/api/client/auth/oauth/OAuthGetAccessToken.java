/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.AbstractOAuthGetToken;
import com.google.api.client.auth.oauth.OAuthParameters;

public class OAuthGetAccessToken
extends AbstractOAuthGetToken {
    public String temporaryToken;
    public String verifier;

    public OAuthGetAccessToken(String string2) {
        super(string2);
    }

    @Override
    public OAuthParameters createParameters() {
        OAuthParameters oAuthParameters = super.createParameters();
        oAuthParameters.token = this.temporaryToken;
        oAuthParameters.verifier = this.verifier;
        return oAuthParameters;
    }
}

