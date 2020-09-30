/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.oauth;

import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.auth.oauth.OAuthSigner;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import java.security.GeneralSecurityException;
import java.security.Key;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class OAuthHmacSigner
implements OAuthSigner {
    public String clientSharedSecret;
    public String tokenSharedSecret;

    @Override
    public String computeSignature(String string2) throws GeneralSecurityException {
        Object object = new StringBuilder();
        Object object2 = this.clientSharedSecret;
        if (object2 != null) {
            ((StringBuilder)object).append(OAuthParameters.escape((String)object2));
        }
        ((StringBuilder)object).append('&');
        object2 = this.tokenSharedSecret;
        if (object2 != null) {
            ((StringBuilder)object).append(OAuthParameters.escape((String)object2));
        }
        object2 = new SecretKeySpec(StringUtils.getBytesUtf8(((StringBuilder)object).toString()), "HmacSHA1");
        object = Mac.getInstance("HmacSHA1");
        ((Mac)object).init((Key)object2);
        return Base64.encodeBase64String(((Mac)object).doFinal(StringUtils.getBytesUtf8(string2)));
    }

    @Override
    public String getSignatureMethod() {
        return "HMAC-SHA1";
    }
}

