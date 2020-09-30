package com.sun.mail.imap.protocol;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class BASE64MailboxDecoder {
   static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
   private static final byte[] pem_convert_array = new byte[256];

   static {
      byte var0 = 0;

      int var1;
      for(var1 = 0; var1 < 255; ++var1) {
         pem_convert_array[var1] = (byte)-1;
      }

      var1 = var0;

      while(true) {
         char[] var2 = pem_array;
         if (var1 >= var2.length) {
            return;
         }

         pem_convert_array[var2[var1]] = (byte)((byte)var1);
         ++var1;
      }
   }

   protected static int base64decode(char[] var0, int var1, CharacterIterator var2) {
      boolean var3 = true;
      int var4 = var1;
      boolean var10 = var3;

      while(true) {
         int var12 = -1;
         boolean var5 = var10;

         while(true) {
            byte var6 = (byte)var2.next();
            if (var6 == -1) {
               var1 = var4;
               return var1;
            }

            if (var6 == 45) {
               var1 = var4;
               if (var5) {
                  var0[var4] = (char)38;
                  var1 = var4 + 1;
               }

               return var1;
            }

            boolean var7 = false;
            var5 = false;
            byte var8 = (byte)var2.next();
            var1 = var4;
            if (var8 == -1) {
               return var1;
            }

            if (var8 == 45) {
               var1 = var4;
               return var1;
            }

            byte[] var9 = pem_convert_array;
            byte var11 = var9[var6 & 255];
            var8 = var9[var8 & 255];
            var11 = (byte)(var11 << 2 & 252 | var8 >>> 4 & 3);
            if (var12 != -1) {
               var0[var4] = (char)((char)(var12 << 8 | var11 & 255));
               ++var4;
               var12 = -1;
            } else {
               var12 = var11 & 255;
            }

            var6 = (byte)var2.next();
            if (var6 == 61) {
               var5 = var7;
            } else {
               var1 = var4;
               if (var6 == -1) {
                  return var1;
               }

               if (var6 == 45) {
                  var1 = var4;
                  return var1;
               }

               var6 = pem_convert_array[var6 & 255];
               var11 = (byte)(var8 << 4 & 240 | var6 >>> 2 & 15);
               if (var12 != -1) {
                  var0[var4] = (char)((char)(var12 << 8 | var11 & 255));
                  ++var4;
                  var12 = -1;
               } else {
                  var12 = var11 & 255;
               }

               var8 = (byte)var2.next();
               if (var8 == 61) {
                  var5 = var7;
               } else {
                  var1 = var4;
                  if (var8 == -1) {
                     return var1;
                  }

                  if (var8 == 45) {
                     var1 = var4;
                     return var1;
                  }

                  var11 = (byte)(var6 << 6 & 192 | pem_convert_array[var8 & 255] & 63);
                  if (var12 != -1) {
                     var0[var4] = (char)((char)(var12 << 8 | var11 & 255));
                     ++var4;
                     var10 = var5;
                     break;
                  }

                  var12 = var11 & 255;
                  var5 = var7;
               }
            }
         }
      }
   }

   public static String decode(String var0) {
      if (var0 != null && var0.length() != 0) {
         char[] var1 = new char[var0.length()];
         StringCharacterIterator var2 = new StringCharacterIterator(var0);
         char var3 = var2.first();
         boolean var4 = false;

         int var5;
         for(var5 = 0; var3 != '\uffff'; var3 = var2.next()) {
            if (var3 == '&') {
               var5 = base64decode(var1, var5, var2);
               var4 = true;
            } else {
               var1[var5] = (char)var3;
               ++var5;
            }
         }

         if (var4) {
            var0 = new String(var1, 0, var5);
         }

         return var0;
      } else {
         return var0;
      }
   }
}
