package org.apache.http.impl.auth;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.params.AuthParams;
import org.apache.http.message.BasicHeaderValueFormatter;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.BufferedHeader;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class DigestScheme extends RFC2617Scheme {
   private static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final int QOP_AUTH = 2;
   private static final int QOP_AUTH_INT = 1;
   private static final int QOP_MISSING = 0;
   private static final int QOP_UNKNOWN = -1;
   private String a1;
   private String a2;
   private String cnonce;
   private boolean complete = false;
   private String lastNonce;
   private long nounceCount;

   public static String createCnonce() {
      SecureRandom var0 = new SecureRandom();
      byte[] var1 = new byte[8];
      var0.nextBytes(var1);
      return encode(var1);
   }

   private Header createDigestHeader(Credentials var1) throws AuthenticationException {
      String var2 = this.getParameter("uri");
      String var3 = this.getParameter("realm");
      String var4 = this.getParameter("nonce");
      String var5 = this.getParameter("opaque");
      String var6 = this.getParameter("methodname");
      String var7 = this.getParameter("algorithm");
      if (var2 == null) {
         throw new IllegalStateException("URI may not be null");
      } else if (var3 != null) {
         if (var4 == null) {
            throw new IllegalStateException("Nonce may not be null");
         } else {
            String var8 = this.getParameter("qop");
            byte var10;
            if (var8 != null) {
               StringTokenizer var9 = new StringTokenizer(var8, ",");

               while(true) {
                  if (!var9.hasMoreTokens()) {
                     var10 = -1;
                     break;
                  }

                  if (var9.nextToken().trim().equals("auth")) {
                     var10 = 2;
                     break;
                  }
               }
            } else {
               var10 = 0;
            }

            StringBuilder var17;
            if (var10 == -1) {
               var17 = new StringBuilder();
               var17.append("None of the qop methods is supported: ");
               var17.append(var8);
               throw new AuthenticationException(var17.toString());
            } else {
               var8 = var7;
               if (var7 == null) {
                  var8 = "MD5";
               }

               String var22 = this.getParameter("charset");
               var7 = var22;
               if (var22 == null) {
                  var7 = "ISO-8859-1";
               }

               var22 = "MD5";
               if (!var8.equalsIgnoreCase("MD5-sess")) {
                  var22 = var8;
               }

               MessageDigest var11;
               try {
                  var11 = createMessageDigest(var22);
               } catch (UnsupportedDigestAlgorithmException var16) {
                  var17 = new StringBuilder();
                  var17.append("Unsuppported digest algorithm: ");
                  var17.append(var22);
                  throw new AuthenticationException(var17.toString());
               }

               String var12 = var1.getUserPrincipal().getName();
               var22 = var1.getPassword();
               if (var4.equals(this.lastNonce)) {
                  ++this.nounceCount;
               } else {
                  this.nounceCount = 1L;
                  this.cnonce = null;
                  this.lastNonce = var4;
               }

               String var23 = var7;
               StringBuilder var13 = new StringBuilder(256);
               (new Formatter(var13, Locale.US)).format("%08x", this.nounceCount);
               String var14 = var13.toString();
               if (this.cnonce == null) {
                  this.cnonce = createCnonce();
               }

               this.a1 = null;
               this.a2 = null;
               if (var8.equalsIgnoreCase("MD5-sess")) {
                  var13.setLength(0);
                  var13.append(var12);
                  var13.append(':');
                  var13.append(var3);
                  var13.append(':');
                  var13.append(var22);
                  var7 = encode(var11.digest(EncodingUtils.getBytes(var13.toString(), var7)));
                  var13.setLength(0);
                  var13.append(var7);
                  var13.append(':');
                  var13.append(var4);
                  var13.append(':');
                  var13.append(this.cnonce);
                  this.a1 = var13.toString();
               } else {
                  var13.setLength(0);
                  var13.append(var12);
                  var13.append(':');
                  var13.append(var3);
                  var13.append(':');
                  var13.append(var22);
                  this.a1 = var13.toString();
               }

               var7 = var4;
               String var19 = encode(var11.digest(EncodingUtils.getBytes(this.a1, var23)));
               StringBuilder var20;
               if (var10 == 2) {
                  var20 = new StringBuilder();
                  var20.append(var6);
                  var20.append(':');
                  var20.append(var2);
                  this.a2 = var20.toString();
               } else {
                  if (var10 == 1) {
                     throw new AuthenticationException("qop-int method is not suppported");
                  }

                  var20 = new StringBuilder();
                  var20.append(var6);
                  var20.append(':');
                  var20.append(var2);
                  this.a2 = var20.toString();
               }

               var23 = encode(var11.digest(EncodingUtils.getBytes(this.a2, var23)));
               var4 = "auth-int";
               if (var10 == 0) {
                  var13.setLength(0);
                  var13.append(var19);
                  var13.append(':');
                  var13.append(var7);
                  var13.append(':');
                  var13.append(var23);
                  var19 = var13.toString();
               } else {
                  var13.setLength(0);
                  var13.append(var19);
                  var13.append(':');
                  var13.append(var7);
                  var13.append(':');
                  var13.append(var14);
                  var13.append(':');
                  var13.append(this.cnonce);
                  var13.append(':');
                  if (var10 == 1) {
                     var19 = "auth-int";
                  } else {
                     var19 = "auth";
                  }

                  var13.append(var19);
                  var13.append(':');
                  var13.append(var23);
                  var19 = var13.toString();
               }

               var19 = encode(var11.digest(EncodingUtils.getAsciiBytes(var19)));
               CharArrayBuffer var27 = new CharArrayBuffer(128);
               if (this.isProxy()) {
                  var27.append("Proxy-Authorization");
               } else {
                  var27.append("Authorization");
               }

               var27.append(": Digest ");
               ArrayList var26 = new ArrayList(20);
               var26.add(new BasicNameValuePair("username", var12));
               var26.add(new BasicNameValuePair("realm", var3));
               var26.add(new BasicNameValuePair("nonce", var7));
               var26.add(new BasicNameValuePair("uri", var2));
               var26.add(new BasicNameValuePair("response", var19));
               if (var10 != 0) {
                  if (var10 == 1) {
                     var19 = var4;
                  } else {
                     var19 = "auth";
                  }

                  var26.add(new BasicNameValuePair("qop", var19));
                  var26.add(new BasicNameValuePair("nc", var14));
                  var26.add(new BasicNameValuePair("cnonce", this.cnonce));
               }

               if (var8 != null) {
                  var26.add(new BasicNameValuePair("algorithm", var8));
               }

               if (var5 != null) {
                  var26.add(new BasicNameValuePair("opaque", var5));
               }

               for(int var25 = 0; var25 < var26.size(); ++var25) {
                  BasicNameValuePair var21 = (BasicNameValuePair)var26.get(var25);
                  if (var25 > 0) {
                     var27.append(", ");
                  }

                  boolean var15;
                  if (!"nc".equals(var21.getName()) && !"qop".equals(var21.getName())) {
                     var15 = false;
                  } else {
                     var15 = true;
                  }

                  BasicHeaderValueFormatter.DEFAULT.formatNameValuePair(var27, var21, var15 ^ true);
               }

               return new BufferedHeader(var27);
            }
         }
      } else {
         throw new IllegalStateException("Realm may not be null");
      }
   }

   private static MessageDigest createMessageDigest(String var0) throws UnsupportedDigestAlgorithmException {
      try {
         MessageDigest var3 = MessageDigest.getInstance(var0);
         return var3;
      } catch (Exception var2) {
         StringBuilder var1 = new StringBuilder();
         var1.append("Unsupported algorithm in HTTP Digest authentication: ");
         var1.append(var0);
         throw new UnsupportedDigestAlgorithmException(var1.toString());
      }
   }

   private static String encode(byte[] var0) {
      int var1 = var0.length;
      char[] var2 = new char[var1 * 2];

      for(int var3 = 0; var3 < var1; ++var3) {
         byte var4 = var0[var3];
         byte var5 = var0[var3];
         int var6 = var3 * 2;
         char[] var7 = HEXADECIMAL;
         var2[var6] = (char)var7[(var5 & 240) >> 4];
         var2[var6 + 1] = (char)var7[var4 & 15];
      }

      return new String(var2);
   }

   public Header authenticate(Credentials var1, HttpRequest var2) throws AuthenticationException {
      if (var1 != null) {
         if (var2 != null) {
            this.getParameters().put("methodname", var2.getRequestLine().getMethod());
            this.getParameters().put("uri", var2.getRequestLine().getUri());
            if (this.getParameter("charset") == null) {
               String var3 = AuthParams.getCredentialCharset(var2.getParams());
               this.getParameters().put("charset", var3);
            }

            return this.createDigestHeader(var1);
         } else {
            throw new IllegalArgumentException("HTTP request may not be null");
         }
      } else {
         throw new IllegalArgumentException("Credentials may not be null");
      }
   }

   String getA1() {
      return this.a1;
   }

   String getA2() {
      return this.a2;
   }

   String getCnonce() {
      return this.cnonce;
   }

   public String getSchemeName() {
      return "digest";
   }

   public boolean isComplete() {
      return "true".equalsIgnoreCase(this.getParameter("stale")) ? false : this.complete;
   }

   public boolean isConnectionBased() {
      return false;
   }

   public void overrideParamter(String var1, String var2) {
      this.getParameters().put(var1, var2);
   }

   public void processChallenge(Header var1) throws MalformedChallengeException {
      super.processChallenge(var1);
      if (this.getParameter("realm") != null) {
         if (this.getParameter("nonce") != null) {
            this.complete = true;
         } else {
            throw new MalformedChallengeException("missing nonce in challenge");
         }
      } else {
         throw new MalformedChallengeException("missing realm in challenge");
      }
   }
}
