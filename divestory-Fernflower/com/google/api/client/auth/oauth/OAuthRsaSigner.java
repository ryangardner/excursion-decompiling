package com.google.api.client.auth.oauth;

import com.google.api.client.util.Base64;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

public final class OAuthRsaSigner implements OAuthSigner {
   public PrivateKey privateKey;

   public String computeSignature(String var1) throws GeneralSecurityException {
      Signature var2 = SecurityUtils.getSha1WithRsaSignatureAlgorithm();
      byte[] var3 = StringUtils.getBytesUtf8(var1);
      return Base64.encodeBase64String(SecurityUtils.sign(var2, this.privateKey, var3));
   }

   public String getSignatureMethod() {
      return "RSA-SHA1";
   }
}
