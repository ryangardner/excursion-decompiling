package com.sun.mail.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ASCIIUtility {
   private ASCIIUtility() {
   }

   public static byte[] getBytes(InputStream var0) throws IOException {
      int var1;
      byte[] var4;
      if (var0 instanceof ByteArrayInputStream) {
         var1 = var0.available();
         byte[] var2 = new byte[var1];
         var0.read(var2, 0, var1);
         var4 = var2;
      } else {
         ByteArrayOutputStream var5 = new ByteArrayOutputStream();
         byte[] var3 = new byte[1024];

         while(true) {
            var1 = var0.read(var3, 0, 1024);
            if (var1 == -1) {
               var4 = var5.toByteArray();
               break;
            }

            var5.write(var3, 0, var1);
         }
      }

      return var4;
   }

   public static byte[] getBytes(String var0) {
      char[] var4 = var0.toCharArray();
      int var1 = var4.length;
      byte[] var2 = new byte[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = (byte)((byte)var4[var3]);
      }

      return var2;
   }

   public static int parseInt(byte[] var0, int var1, int var2) throws NumberFormatException {
      return parseInt(var0, var1, var2, 10);
   }

   public static int parseInt(byte[] var0, int var1, int var2, int var3) throws NumberFormatException {
      if (var0 == null) {
         throw new NumberFormatException("null");
      } else if (var2 <= var1) {
         throw new NumberFormatException("illegal number");
      } else {
         byte var4 = var0[var1];
         int var5 = 0;
         int var6;
         int var7;
         boolean var8;
         if (var4 == 45) {
            var6 = Integer.MIN_VALUE;
            var7 = var1 + 1;
            var8 = true;
         } else {
            var6 = -2147483647;
            var7 = var1;
            var8 = false;
         }

         int var9 = var6 / var3;
         int var11 = var7;
         if (var7 < var2) {
            var11 = Character.digit((char)var0[var7], var3);
            if (var11 < 0) {
               StringBuilder var10 = new StringBuilder("illegal number: ");
               var10.append(toString(var0, var1, var2));
               throw new NumberFormatException(var10.toString());
            }

            var5 = -var11;
            var11 = var7 + 1;
         }

         while(var11 < var2) {
            var7 = Character.digit((char)var0[var11], var3);
            if (var7 < 0) {
               throw new NumberFormatException("illegal number");
            }

            if (var5 < var9) {
               throw new NumberFormatException("illegal number");
            }

            var5 *= var3;
            if (var5 < var6 + var7) {
               throw new NumberFormatException("illegal number");
            }

            var5 -= var7;
            ++var11;
         }

         if (var8) {
            if (var11 > var1 + 1) {
               return var5;
            } else {
               throw new NumberFormatException("illegal number");
            }
         } else {
            return -var5;
         }
      }
   }

   public static long parseLong(byte[] var0, int var1, int var2) throws NumberFormatException {
      return parseLong(var0, var1, var2, 10);
   }

   public static long parseLong(byte[] var0, int var1, int var2, int var3) throws NumberFormatException {
      int var4 = var1;
      if (var0 == null) {
         throw new NumberFormatException("null");
      } else {
         long var6 = 0L;
         if (var2 <= var1) {
            throw new NumberFormatException("illegal number");
         } else {
            long var8;
            boolean var10;
            if (var0[var1] == 45) {
               var4 = var1 + 1;
               var8 = Long.MIN_VALUE;
               var10 = true;
            } else {
               var8 = -9223372036854775807L;
               var10 = false;
            }

            long var11 = (long)var3;
            long var13 = var8 / var11;
            int var15 = var4;
            if (var4 < var2) {
               var15 = Character.digit((char)var0[var4], var3);
               if (var15 < 0) {
                  StringBuilder var16 = new StringBuilder("illegal number: ");
                  var16.append(toString(var0, var1, var2));
                  throw new NumberFormatException(var16.toString());
               }

               var6 = (long)(-var15);
               var15 = var4 + 1;
            }

            while(var15 < var2) {
               var4 = Character.digit((char)var0[var15], var3);
               if (var4 < 0) {
                  throw new NumberFormatException("illegal number");
               }

               if (var6 < var13) {
                  throw new NumberFormatException("illegal number");
               }

               var6 *= var11;
               long var17 = (long)var4;
               if (var6 < var8 + var17) {
                  throw new NumberFormatException("illegal number");
               }

               var6 -= var17;
               ++var15;
            }

            if (var10) {
               if (var15 > var1 + 1) {
                  return var6;
               } else {
                  throw new NumberFormatException("illegal number");
               }
            } else {
               return -var6;
            }
         }
      }
   }

   public static String toString(ByteArrayInputStream var0) {
      int var1 = var0.available();
      char[] var2 = new char[var1];
      byte[] var3 = new byte[var1];
      int var4 = 0;
      var0.read(var3, 0, var1);

      while(var4 < var1) {
         var2[var4] = (char)((char)(var3[var4] & 255));
         ++var4;
      }

      return new String(var2);
   }

   public static String toString(byte[] var0, int var1, int var2) {
      int var3 = var2 - var1;
      char[] var4 = new char[var3];

      for(var2 = 0; var2 < var3; ++var1) {
         var4[var2] = (char)((char)(var0[var1] & 255));
         ++var2;
      }

      return new String(var4);
   }
}
