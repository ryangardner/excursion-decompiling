package com.google.api.client.json.webtoken;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.SecurityUtils;
import com.google.api.client.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.X509TrustManager;

public class JsonWebSignature extends JsonWebToken {
   private final byte[] signatureBytes;
   private final byte[] signedContentBytes;

   public JsonWebSignature(JsonWebSignature.Header var1, JsonWebToken.Payload var2, byte[] var3, byte[] var4) {
      super(var1, var2);
      this.signatureBytes = (byte[])Preconditions.checkNotNull(var3);
      this.signedContentBytes = (byte[])Preconditions.checkNotNull(var4);
   }

   private static X509TrustManager getDefaultX509TrustManager() {
      // $FF: Couldn't be decompiled
   }

   public static JsonWebSignature parse(JsonFactory var0, String var1) throws IOException {
      return parser(var0).parse(var1);
   }

   public static JsonWebSignature.Parser parser(JsonFactory var0) {
      return new JsonWebSignature.Parser(var0);
   }

   public static String signUsingRsaSha256(PrivateKey var0, JsonFactory var1, JsonWebSignature.Header var2, JsonWebToken.Payload var3) throws GeneralSecurityException, IOException {
      StringBuilder var4 = new StringBuilder();
      var4.append(Base64.encodeBase64URLSafeString(var1.toByteArray(var2)));
      var4.append(".");
      var4.append(Base64.encodeBase64URLSafeString(var1.toByteArray(var3)));
      String var6 = var4.toString();
      byte[] var7 = StringUtils.getBytesUtf8(var6);
      byte[] var5 = SecurityUtils.sign(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), var0, var7);
      StringBuilder var8 = new StringBuilder();
      var8.append(var6);
      var8.append(".");
      var8.append(Base64.encodeBase64URLSafeString(var5));
      return var8.toString();
   }

   public JsonWebSignature.Header getHeader() {
      return (JsonWebSignature.Header)super.getHeader();
   }

   public final byte[] getSignatureBytes() {
      byte[] var1 = this.signatureBytes;
      return Arrays.copyOf(var1, var1.length);
   }

   public final byte[] getSignedContentBytes() {
      byte[] var1 = this.signedContentBytes;
      return Arrays.copyOf(var1, var1.length);
   }

   public final X509Certificate verifySignature() throws GeneralSecurityException {
      X509TrustManager var1 = getDefaultX509TrustManager();
      return var1 == null ? null : this.verifySignature(var1);
   }

   public final X509Certificate verifySignature(X509TrustManager var1) throws GeneralSecurityException {
      List var2 = this.getHeader().getX509Certificates();
      return var2 != null && !var2.isEmpty() && "RS256".equals(this.getHeader().getAlgorithm()) ? SecurityUtils.verify(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), var1, var2, this.signatureBytes, this.signedContentBytes) : null;
   }

   public final boolean verifySignature(PublicKey var1) throws GeneralSecurityException {
      return "RS256".equals(this.getHeader().getAlgorithm()) ? SecurityUtils.verify(SecurityUtils.getSha256WithRsaSignatureAlgorithm(), var1, this.signatureBytes, this.signedContentBytes) : false;
   }

   public static class Header extends JsonWebToken.Header {
      @Key("alg")
      private String algorithm;
      @Key("crit")
      private List<String> critical;
      @Key("jwk")
      private String jwk;
      @Key("jku")
      private String jwkUrl;
      @Key("kid")
      private String keyId;
      @Key("x5c")
      private ArrayList<String> x509Certificates;
      @Key("x5t")
      private String x509Thumbprint;
      @Key("x5u")
      private String x509Url;

      public JsonWebSignature.Header clone() {
         return (JsonWebSignature.Header)super.clone();
      }

      public final String getAlgorithm() {
         return this.algorithm;
      }

      public final List<String> getCritical() {
         List var1 = this.critical;
         return var1 != null && !var1.isEmpty() ? new ArrayList(this.critical) : null;
      }

      public final String getJwk() {
         return this.jwk;
      }

      public final String getJwkUrl() {
         return this.jwkUrl;
      }

      public final String getKeyId() {
         return this.keyId;
      }

      public final List<String> getX509Certificates() {
         return new ArrayList(this.x509Certificates);
      }

      public final String getX509Thumbprint() {
         return this.x509Thumbprint;
      }

      public final String getX509Url() {
         return this.x509Url;
      }

      public JsonWebSignature.Header set(String var1, Object var2) {
         return (JsonWebSignature.Header)super.set(var1, var2);
      }

      public JsonWebSignature.Header setAlgorithm(String var1) {
         this.algorithm = var1;
         return this;
      }

      public JsonWebSignature.Header setCritical(List<String> var1) {
         this.critical = new ArrayList(var1);
         return this;
      }

      public JsonWebSignature.Header setJwk(String var1) {
         this.jwk = var1;
         return this;
      }

      public JsonWebSignature.Header setJwkUrl(String var1) {
         this.jwkUrl = var1;
         return this;
      }

      public JsonWebSignature.Header setKeyId(String var1) {
         this.keyId = var1;
         return this;
      }

      public JsonWebSignature.Header setType(String var1) {
         super.setType(var1);
         return this;
      }

      public JsonWebSignature.Header setX509Certificates(List<String> var1) {
         this.x509Certificates = new ArrayList(var1);
         return this;
      }

      public JsonWebSignature.Header setX509Thumbprint(String var1) {
         this.x509Thumbprint = var1;
         return this;
      }

      public JsonWebSignature.Header setX509Url(String var1) {
         this.x509Url = var1;
         return this;
      }
   }

   public static final class Parser {
      private Class<? extends JsonWebSignature.Header> headerClass = JsonWebSignature.Header.class;
      private final JsonFactory jsonFactory;
      private Class<? extends JsonWebToken.Payload> payloadClass = JsonWebToken.Payload.class;

      public Parser(JsonFactory var1) {
         this.jsonFactory = (JsonFactory)Preconditions.checkNotNull(var1);
      }

      public Class<? extends JsonWebSignature.Header> getHeaderClass() {
         return this.headerClass;
      }

      public JsonFactory getJsonFactory() {
         return this.jsonFactory;
      }

      public Class<? extends JsonWebToken.Payload> getPayloadClass() {
         return this.payloadClass;
      }

      public JsonWebSignature parse(String var1) throws IOException {
         int var2 = var1.indexOf(46);
         boolean var3 = true;
         boolean var4;
         if (var2 != -1) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         byte[] var5 = Base64.decodeBase64(var1.substring(0, var2));
         int var6 = var2 + 1;
         int var7 = var1.indexOf(46, var6);
         if (var7 != -1) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         var2 = var7 + 1;
         if (var1.indexOf(46, var2) == -1) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         byte[] var8 = Base64.decodeBase64(var1.substring(var6, var7));
         byte[] var9 = Base64.decodeBase64(var1.substring(var2));
         byte[] var10 = StringUtils.getBytesUtf8(var1.substring(0, var7));
         JsonWebSignature.Header var11 = (JsonWebSignature.Header)this.jsonFactory.fromInputStream(new ByteArrayInputStream(var5), this.headerClass);
         if (var11.getAlgorithm() != null) {
            var4 = var3;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4);
         return new JsonWebSignature(var11, (JsonWebToken.Payload)this.jsonFactory.fromInputStream(new ByteArrayInputStream(var8), this.payloadClass), var9, var10);
      }

      public JsonWebSignature.Parser setHeaderClass(Class<? extends JsonWebSignature.Header> var1) {
         this.headerClass = var1;
         return this;
      }

      public JsonWebSignature.Parser setPayloadClass(Class<? extends JsonWebToken.Payload> var1) {
         this.payloadClass = var1;
         return this;
      }
   }
}
