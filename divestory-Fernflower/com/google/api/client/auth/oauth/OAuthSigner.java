package com.google.api.client.auth.oauth;

import java.security.GeneralSecurityException;

public interface OAuthSigner {
   String computeSignature(String var1) throws GeneralSecurityException;

   String getSignatureMethod();
}
