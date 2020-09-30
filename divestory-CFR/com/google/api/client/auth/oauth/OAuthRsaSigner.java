/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.util.Base64;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

public final class OAuthRsaSigner
implements OAuthSigner {
    public PrivateKey privateKey;

    @Override
    public String computeSignature(String arrby) throws GeneralSecurityException {
        Signature signature = SecurityUtils.getSha1WithRsaSignatureAlgorithm();
        arrby = StringUtils.getBytesUtf8((String)arrby);
        return Base64.encodeBase64String(SecurityUtils.sign(signature, this.privateKey, arrby));
    }

    @Override
    public String getSignatureMethod() {
        return "RSA-SHA1";
    }
}

