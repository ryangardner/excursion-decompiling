package com.google.api.client.auth.oauth;

public class OAuthGetAccessToken extends AbstractOAuthGetToken {
   public String temporaryToken;
   public String verifier;

   public OAuthGetAccessToken(String var1) {
      super(var1);
   }

   public OAuthParameters createParameters() {
      OAuthParameters var1 = super.createParameters();
      var1.token = this.temporaryToken;
      var1.verifier = this.verifier;
      return var1;
   }
}
