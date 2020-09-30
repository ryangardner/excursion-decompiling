package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedParser;
import java.io.IOException;

public abstract class AbstractOAuthGetToken extends GenericUrl {
   public String consumerKey;
   public OAuthSigner signer;
   public HttpTransport transport;
   protected boolean usePost;

   protected AbstractOAuthGetToken(String var1) {
      super(var1);
   }

   public OAuthParameters createParameters() {
      OAuthParameters var1 = new OAuthParameters();
      var1.consumerKey = this.consumerKey;
      var1.signer = this.signer;
      return var1;
   }

   public final OAuthCredentialsResponse execute() throws IOException {
      HttpRequestFactory var1 = this.transport.createRequestFactory();
      String var2;
      if (this.usePost) {
         var2 = "POST";
      } else {
         var2 = "GET";
      }

      HttpRequest var4 = var1.buildRequest(var2, this, (HttpContent)null);
      this.createParameters().intercept(var4);
      HttpResponse var3 = var4.execute();
      var3.setContentLoggingLimit(0);
      OAuthCredentialsResponse var5 = new OAuthCredentialsResponse();
      UrlEncodedParser.parse((String)var3.parseAsString(), var5);
      return var5;
   }
}
