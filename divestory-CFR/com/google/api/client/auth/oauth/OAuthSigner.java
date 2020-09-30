/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import java.security.GeneralSecurityException;

public interface OAuthSigner {
    public String computeSignature(String var1) throws GeneralSecurityException;

    public String getSignatureMethod();
}

