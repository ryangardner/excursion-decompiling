/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class OAuthAuthorizeTemporaryTokenUrl
extends GenericUrl {
    @Key(value="oauth_token")
    public String temporaryToken;

    public OAuthAuthorizeTemporaryTokenUrl(String string2) {
        super(string2);
    }
}

