/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.util.Key;

public final class OAuthCredentialsResponse {
    @Key(value="oauth_callback_confirmed")
    public Boolean callbackConfirmed;
    @Key(value="oauth_token")
    public String token;
    @Key(value="oauth_token_secret")
    public String tokenSecret;
}

