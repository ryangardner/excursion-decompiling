/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth2;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenErrorResponse;
import com.google.api.client.auth.oauth2.TokenResponse;
import java.io.IOException;

public interface CredentialRefreshListener {
    public void onTokenErrorResponse(Credential var1, TokenErrorResponse var2) throws IOException;

    public void onTokenResponse(Credential var1, TokenResponse var2) throws IOException;
}

