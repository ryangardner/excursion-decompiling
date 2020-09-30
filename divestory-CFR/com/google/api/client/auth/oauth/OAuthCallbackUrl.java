/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class OAuthCallbackUrl
extends GenericUrl {
    @Key(value="oauth_token")
    public String token;
    @Key(value="oauth_verifier")
    public String verifier;

    public OAuthCallbackUrl(String string2) {
        super(string2);
    }
}

