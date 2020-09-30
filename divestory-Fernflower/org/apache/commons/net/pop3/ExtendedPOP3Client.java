package org.apache.commons.net.pop3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.net.util.Base64;

public class ExtendedPOP3Client extends POP3SClient {
   public ExtendedPOP3Client() throws NoSuchAlgorithmException {
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

   public boolean auth(ExtendedPOP3Client.AUTH_METHOD var1, String var2, String var3) throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
      int var4 = this.sendCommand(13, var1.getAuthName());
      boolean var5 = false;
      boolean var6 = false;
      if (var4 != 2) {
         return false;
      } else {
         var4 = null.$SwitchMap$org$apache$commons$net$pop3$ExtendedPOP3Client$AUTH_METHOD[var1.ordinal()];
         if (var4 != 1) {
            if (var4 != 2) {
               return false;
            } else {
               byte[] var9 = Base64.decodeBase64(this.getReplyString().substring(2).trim());
               Mac var7 = Mac.getInstance("HmacMD5");
               var7.init(new SecretKeySpec(var3.getBytes(this.getCharsetName()), "HmacMD5"));
               var9 = this._convertToHexString(var7.doFinal(var9)).getBytes(this.getCharsetName());
               byte[] var11 = var2.getBytes(this.getCharsetName());
               byte[] var10 = new byte[var11.length + 1 + var9.length];
               System.arraycopy(var11, 0, var10, 0, var11.length);
               var10[var11.length] = (byte)32;
               System.arraycopy(var9, 0, var10, var11.length + 1, var9.length);
               if (this.sendCommand(Base64.encodeBase64StringUnChunked(var10)) == 0) {
                  var6 = true;
               }

               return var6;
            }
         } else {
            StringBuilder var8 = new StringBuilder();
            var8.append("\u0000");
            var8.append(var2);
            var8.append("\u0000");
            var8.append(var3);
            var6 = var5;
            if (this.sendCommand(new String(Base64.encodeBase64(var8.toString().getBytes(this.getCharsetName())), this.getCharsetName())) == 0) {
               var6 = true;
            }

            return var6;
         }
      }
   }

   public static enum AUTH_METHOD {
      CRAM_MD5,
      PLAIN("PLAIN");

      private final String methodName;

      static {
         ExtendedPOP3Client.AUTH_METHOD var0 = new ExtendedPOP3Client.AUTH_METHOD("CRAM_MD5", 1, "CRAM-MD5");
         CRAM_MD5 = var0;
      }

      private AUTH_METHOD(String var3) {
         this.methodName = var3;
      }

      public final String getAuthName() {
         return this.methodName;
      }
   }
}
