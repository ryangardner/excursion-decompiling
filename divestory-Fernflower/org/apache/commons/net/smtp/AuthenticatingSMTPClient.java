package org.apache.commons.net.smtp;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import org.apache.commons.net.util.Base64;

public class AuthenticatingSMTPClient extends SMTPSClient {
   public AuthenticatingSMTPClient() {
   }

   public AuthenticatingSMTPClient(String var1) {
      super(var1);
   }

   public AuthenticatingSMTPClient(String var1, String var2) {
      super(var1, false, var2);
   }

   public AuthenticatingSMTPClient(String var1, boolean var2) {
      super(var1, var2);
   }

   public AuthenticatingSMTPClient(String var1, boolean var2, String var3) {
      super(var1, var2, var3);
   }

   public AuthenticatingSMTPClient(boolean var1, SSLContext var2) {
      super(var1, var2);
   }

   private String _convertToHexString(byte[] var1) {
      StringBuilder var2 = new StringBuilder(var1.length * 2);
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var1[var4] & 255;
         if (var5 <= 15) {
            var2.append("0");
         }

         var2.append(Integer.toHexString(var5));
      }

      return var2.toString();
   }

   public boolean auth(AuthenticatingSMTPClient.AUTH_METHOD var1, String var2, String var3) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
      if (!SMTPReply.isPositiveIntermediate(this.sendCommand(14, AuthenticatingSMTPClient.AUTH_METHOD.getAuthName(var1)))) {
         return false;
      } else if (var1.equals(AuthenticatingSMTPClient.AUTH_METHOD.PLAIN)) {
         StringBuilder var6 = new StringBuilder();
         var6.append("\u0000");
         var6.append(var2);
         var6.append("\u0000");
         var6.append(var3);
         return SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(var6.toString().getBytes(this.getCharsetName()))));
      } else if (var1.equals(AuthenticatingSMTPClient.AUTH_METHOD.CRAM_MD5)) {
         byte[] var5 = Base64.decodeBase64(this.getReplyString().substring(4).trim());
         Mac var4 = Mac.getInstance("HmacMD5");
         var4.init(new SecretKeySpec(var3.getBytes(this.getCharsetName()), "HmacMD5"));
         var5 = this._convertToHexString(var4.doFinal(var5)).getBytes(this.getCharsetName());
         byte[] var7 = var2.getBytes(this.getCharsetName());
         byte[] var8 = new byte[var7.length + 1 + var5.length];
         System.arraycopy(var7, 0, var8, 0, var7.length);
         var8[var7.length] = (byte)32;
         System.arraycopy(var5, 0, var8, var7.length + 1, var5.length);
         return SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(var8)));
      } else if (var1.equals(AuthenticatingSMTPClient.AUTH_METHOD.LOGIN)) {
         return !SMTPReply.isPositiveIntermediate(this.sendCommand(Base64.encodeBase64StringUnChunked(var2.getBytes(this.getCharsetName())))) ? false : SMTPReply.isPositiveCompletion(this.sendCommand(Base64.encodeBase64StringUnChunked(var3.getBytes(this.getCharsetName()))));
      } else {
         return var1.equals(AuthenticatingSMTPClient.AUTH_METHOD.XOAUTH) ? SMTPReply.isPositiveIntermediate(this.sendCommand(Base64.encodeBase64StringUnChunked(var2.getBytes(this.getCharsetName())))) : false;
      }
   }

   public int ehlo(String var1) throws IOException {
      return this.sendCommand(15, var1);
   }

   public boolean elogin() throws IOException {
      String var1 = this.getLocalAddress().getHostName();
      return var1 == null ? false : SMTPReply.isPositiveCompletion(this.ehlo(var1));
   }

   public boolean elogin(String var1) throws IOException {
      return SMTPReply.isPositiveCompletion(this.ehlo(var1));
   }

   public int[] getEnhancedReplyCode() {
      String var1 = this.getReplyString().substring(4);
      int var2 = var1.indexOf(32);
      int var3 = 0;
      String[] var5 = var1.substring(0, var2).split("\\.");

      int[] var4;
      for(var4 = new int[var5.length]; var3 < var5.length; ++var3) {
         var4[var3] = Integer.parseInt(var5[var3]);
      }

      return var4;
   }

   public static enum AUTH_METHOD {
      CRAM_MD5,
      LOGIN,
      PLAIN,
      XOAUTH;

      static {
         AuthenticatingSMTPClient.AUTH_METHOD var0 = new AuthenticatingSMTPClient.AUTH_METHOD("XOAUTH", 3);
         XOAUTH = var0;
      }

      public static final String getAuthName(AuthenticatingSMTPClient.AUTH_METHOD var0) {
         if (var0.equals(PLAIN)) {
            return "PLAIN";
         } else if (var0.equals(CRAM_MD5)) {
            return "CRAM-MD5";
         } else if (var0.equals(LOGIN)) {
            return "LOGIN";
         } else {
            return var0.equals(XOAUTH) ? "XOAUTH" : null;
         }
      }
   }
}
