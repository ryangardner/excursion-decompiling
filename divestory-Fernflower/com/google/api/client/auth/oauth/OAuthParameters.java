package com.google.api.client.auth.oauth;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.util.escape.PercentEscaper;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

public final class OAuthParameters implements HttpExecuteInterceptor, HttpRequestInitializer {
   private static final PercentEscaper ESCAPER = new PercentEscaper("-_.~", false);
   private static final SecureRandom RANDOM = new SecureRandom();
   public String callback;
   public String consumerKey;
   public String nonce;
   public String realm;
   public String signature;
   public String signatureMethod;
   public OAuthSigner signer;
   public String timestamp;
   public String token;
   public String verifier;
   public String version;

   private void appendParameter(StringBuilder var1, String var2, String var3) {
      if (var3 != null) {
         var1.append(' ');
         var1.append(escape(var2));
         var1.append("=\"");
         var1.append(escape(var3));
         var1.append("\",");
      }

   }

   public static String escape(String var0) {
      return ESCAPER.escape(var0);
   }

   private void putParameter(Multiset<OAuthParameters.Parameter> var1, String var2, Object var3) {
      String var4 = escape(var2);
      if (var3 == null) {
         var2 = null;
      } else {
         var2 = escape(var3.toString());
      }

      var1.add(new OAuthParameters.Parameter(var4, var2));
   }

   private void putParameterIfValueNotNull(Multiset<OAuthParameters.Parameter> var1, String var2, String var3) {
      if (var3 != null) {
         this.putParameter(var1, var2, var3);
      }

   }

   public void computeNonce() {
      this.nonce = Long.toHexString(Math.abs(RANDOM.nextLong()));
   }

   public void computeSignature(String var1, GenericUrl var2) throws GeneralSecurityException {
      OAuthSigner var3 = this.signer;
      String var4 = var3.getSignatureMethod();
      this.signatureMethod = var4;
      TreeMultiset var5 = TreeMultiset.create();
      this.putParameterIfValueNotNull(var5, "oauth_callback", this.callback);
      this.putParameterIfValueNotNull(var5, "oauth_consumer_key", this.consumerKey);
      this.putParameterIfValueNotNull(var5, "oauth_nonce", this.nonce);
      this.putParameterIfValueNotNull(var5, "oauth_signature_method", var4);
      this.putParameterIfValueNotNull(var5, "oauth_timestamp", this.timestamp);
      this.putParameterIfValueNotNull(var5, "oauth_token", this.token);
      this.putParameterIfValueNotNull(var5, "oauth_verifier", this.verifier);
      this.putParameterIfValueNotNull(var5, "oauth_version", this.version);
      Iterator var11 = var2.entrySet().iterator();

      while(true) {
         while(true) {
            Entry var6;
            Object var7;
            do {
               if (!var11.hasNext()) {
                  StringBuilder var12 = new StringBuilder();
                  boolean var8 = true;
                  Iterator var13 = var5.elementSet().iterator();

                  while(var13.hasNext()) {
                     OAuthParameters.Parameter var17 = (OAuthParameters.Parameter)var13.next();
                     boolean var9;
                     if (var8) {
                        var9 = false;
                     } else {
                        var12.append('&');
                        var9 = var8;
                     }

                     var12.append(var17.getKey());
                     String var18 = var17.getValue();
                     var8 = var9;
                     if (var18 != null) {
                        var12.append('=');
                        var12.append(var18);
                        var8 = var9;
                     }
                  }

                  String var14;
                  GenericUrl var19;
                  int var20;
                  label68: {
                     var14 = var12.toString();
                     var19 = new GenericUrl();
                     var4 = var2.getScheme();
                     var19.setScheme(var4);
                     var19.setHost(var2.getHost());
                     var19.setPathParts(var2.getPathParts());
                     int var21 = var2.getPort();
                     if (!"http".equals(var4) || var21 != 80) {
                        var20 = var21;
                        if (!"https".equals(var4)) {
                           break label68;
                        }

                        var20 = var21;
                        if (var21 != 443) {
                           break label68;
                        }
                     }

                     var20 = -1;
                  }

                  var19.setPort(var20);
                  String var10 = var19.build();
                  var12 = new StringBuilder();
                  var12.append(escape(var1));
                  var12.append('&');
                  var12.append(escape(var10));
                  var12.append('&');
                  var12.append(escape(var14));
                  this.signature = var3.computeSignature(var12.toString());
                  return;
               }

               var6 = (Entry)var11.next();
               var7 = var6.getValue();
            } while(var7 == null);

            String var15 = (String)var6.getKey();
            if (var7 instanceof Collection) {
               Iterator var16 = ((Collection)var7).iterator();

               while(var16.hasNext()) {
                  this.putParameter(var5, var15, var16.next());
               }
            } else {
               this.putParameter(var5, var15, var7);
            }
         }
      }
   }

   public void computeTimestamp() {
      this.timestamp = Long.toString(System.currentTimeMillis() / 1000L);
   }

   public String getAuthorizationHeader() {
      StringBuilder var1 = new StringBuilder("OAuth");
      this.appendParameter(var1, "realm", this.realm);
      this.appendParameter(var1, "oauth_callback", this.callback);
      this.appendParameter(var1, "oauth_consumer_key", this.consumerKey);
      this.appendParameter(var1, "oauth_nonce", this.nonce);
      this.appendParameter(var1, "oauth_signature", this.signature);
      this.appendParameter(var1, "oauth_signature_method", this.signatureMethod);
      this.appendParameter(var1, "oauth_timestamp", this.timestamp);
      this.appendParameter(var1, "oauth_token", this.token);
      this.appendParameter(var1, "oauth_verifier", this.verifier);
      this.appendParameter(var1, "oauth_version", this.version);
      return var1.substring(0, var1.length() - 1);
   }

   public void initialize(HttpRequest var1) throws IOException {
      var1.setInterceptor(this);
   }

   public void intercept(HttpRequest var1) throws IOException {
      this.computeNonce();
      this.computeTimestamp();

      try {
         this.computeSignature(var1.getRequestMethod(), var1.getUrl());
      } catch (GeneralSecurityException var3) {
         IOException var2 = new IOException();
         var2.initCause(var3);
         throw var2;
      }

      var1.getHeaders().setAuthorization(this.getAuthorizationHeader());
   }

   private static class Parameter implements Comparable<OAuthParameters.Parameter> {
      private final String key;
      private final String value;

      public Parameter(String var1, String var2) {
         this.key = var1;
         this.value = var2;
      }

      public int compareTo(OAuthParameters.Parameter var1) {
         int var2 = this.key.compareTo(var1.key);
         int var3 = var2;
         if (var2 == 0) {
            var3 = this.value.compareTo(var1.value);
         }

         return var3;
      }

      public String getKey() {
         return this.key;
      }

      public String getValue() {
         return this.value;
      }
   }
}
