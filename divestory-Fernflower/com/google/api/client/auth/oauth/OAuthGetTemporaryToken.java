package com.google.api.client.auth.oauth;

public class OAuthGetTemporaryToken extends AbstractOAuthGetToken {
   public String callback;

   public OAuthGetTemporaryToken(String var1) {
      super(var1);
   }

   public OAuthParameters createParameters() {
      OAuthParameters var1 = super.createParameters();
      var1.callback = this.callback;
      return var1;
   }
}
