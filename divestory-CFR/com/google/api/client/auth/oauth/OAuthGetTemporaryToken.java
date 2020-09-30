/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.AbstractOAuthGetToken;
import com.google.api.client.auth.oauth.OAuthParameters;

public class OAuthGetTemporaryToken
extends AbstractOAuthGetToken {
    public String callback;

    public OAuthGetTemporaryToken(String string2) {
        super(string2);
    }

    @Override
    public OAuthParameters createParameters() {
        OAuthParameters oAuthParameters = super.createParameters();
        oAuthParameters.callback = this.callback;
        return oAuthParameters;
    }
}

