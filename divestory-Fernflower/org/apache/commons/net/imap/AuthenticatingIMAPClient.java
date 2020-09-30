package org.apache.commons.net.imap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import org.apache.commons.net.util.Base64;

public class AuthenticatingIMAPClient extends IMAPSClient {
   public AuthenticatingIMAPClient() {
      this("TLS", false);
   }

   public AuthenticatingIMAPClient(String var1) {
      this(var1, false);
   }

   public AuthenticatingIMAPClient(String var1, boolean var2) {
      this(var1, var2, (SSLContext)null);
   }

   public AuthenticatingIMAPClient(String var1, boolean var2, SSLContext var3) {
      super(var1, var2, var3);
   }

   public AuthenticatingIMAPClient(SSLContext var1) {
      this(false, var1);
   }

   public AuthenticatingIMAPClient(boolean var1) {
      this("TLS", var1);
   }

   public AuthenticatingIMAPClient(boolean var1, SSLContext var2) {
      this("TLS", var1, var2);
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

   public boolean auth(AuthenticatingIMAPClient.AUTH_METHOD var1, String var2, String var3) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
      boolean var4 = IMAPReply.isContinuation(this.sendCommand(IMAPCommand.AUTHENTICATE, var1.getAuthName()));
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      if (!var4) {
         return false;
      } else {
         int var9 = null.$SwitchMap$org$apache$commons$net$imap$AuthenticatingIMAPClient$AUTH_METHOD[var1.ordinal()];
         if (var9 != 1) {
            if (var9 != 2) {
               if (var9 != 3) {
                  if (var9 != 4) {
                     return false;
                  } else {
                     var9 = this.sendData(var2);
                     if (var9 == 0) {
                        this.setState(IMAP.IMAPState.AUTH_STATE);
                     }

                     if (var9 == 0) {
                        var8 = true;
                     }

                     return var8;
                  }
               } else if (this.sendData(Base64.encodeBase64StringUnChunked(var2.getBytes(this.getCharsetName()))) != 3) {
                  return false;
               } else {
                  var9 = this.sendData(Base64.encodeBase64StringUnChunked(var3.getBytes(this.getCharsetName())));
                  if (var9 == 0) {
                     this.setState(IMAP.IMAPState.AUTH_STATE);
                  }

                  var8 = var5;
                  if (var9 == 0) {
                     var8 = true;
                  }

                  return var8;
               }
            } else {
               byte[] var10 = Base64.decodeBase64(this.getReplyString().substring(2).trim());
               Mac var12 = Mac.getInstance("HmacMD5");
               var12.init(new SecretKeySpec(var3.getBytes(this.getCharsetName()), "HmacMD5"));
               byte[] var13 = this._convertToHexString(var12.doFinal(var10)).getBytes(this.getCharsetName());
               byte[] var15 = var2.getBytes(this.getCharsetName());
               byte[] var14 = new byte[var15.length + 1 + var13.length];
               System.arraycopy(var15, 0, var14, 0, var15.length);
               var14[var15.length] = (byte)32;
               System.arraycopy(var13, 0, var14, var15.length + 1, var13.length);
               var9 = this.sendData(Base64.encodeBase64StringUnChunked(var14));
               if (var9 == 0) {
                  this.setState(IMAP.IMAPState.AUTH_STATE);
               }

               var8 = var6;
               if (var9 == 0) {
                  var8 = true;
               }

               return var8;
            }
         } else {
            StringBuilder var11 = new StringBuilder();
            var11.append("\u0000");
            var11.append(var2);
            var11.append("\u0000");
            var11.append(var3);
            var9 = this.sendData(Base64.encodeBase64StringUnChunked(var11.toString().getBytes(this.getCharsetName())));
            if (var9 == 0) {
               this.setState(IMAP.IMAPState.AUTH_STATE);
            }

            var8 = var7;
            if (var9 == 0) {
               var8 = true;
            }

            return var8;
         }
      }
   }

   public boolean authenticate(AuthenticatingIMAPClient.AUTH_METHOD var1, String var2, String var3) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
      return this.auth(var1, var2, var3);
   }

   public static enum AUTH_METHOD {
      CRAM_MD5("CRAM-MD5"),
      LOGIN("LOGIN"),
      PLAIN("PLAIN"),
      XOAUTH;

      private final String authName;

      static {
         AuthenticatingIMAPClient.AUTH_METHOD var0 = new AuthenticatingIMAPClient.AUTH_METHOD("XOAUTH", 3, "XOAUTH");
         XOAUTH = var0;
      }

      private AUTH_METHOD(String var3) {
         this.authName = var3;
      }

      public final String getAuthName() {
         return this.authName;
      }
   }
}
