package com.fasterxml.jackson.core.io;

import java.util.Arrays;

public final class CharTypes {
   private static final byte[] HB;
   private static final char[] HC;
   private static final int[] sHexValues;
   private static final int[] sInputCodes;
   private static final int[] sInputCodesComment;
   private static final int[] sInputCodesJsNames;
   private static final int[] sInputCodesUTF8;
   private static final int[] sInputCodesUtf8JsNames;
   private static final int[] sInputCodesWS;
   private static final int[] sOutputEscapes128;

   static {
      char[] var0 = "0123456789ABCDEF".toCharArray();
      HC = var0;
      int var1 = var0.length;
      HB = new byte[var1];
      byte var2 = 0;

      int var3;
      for(var3 = 0; var3 < var1; ++var3) {
         HB[var3] = (byte)((byte)HC[var3]);
      }

      int[] var5 = new int[256];

      for(var3 = 0; var3 < 32; ++var3) {
         var5[var3] = -1;
      }

      var5[34] = 1;
      var5[92] = 1;
      sInputCodes = var5;
      var3 = var5.length;
      int[] var4 = new int[var3];
      System.arraycopy(var5, 0, var4, 0, var3);

      for(var1 = 128; var1 < 256; ++var1) {
         byte var6;
         if ((var1 & 224) == 192) {
            var6 = 2;
         } else if ((var1 & 240) == 224) {
            var6 = 3;
         } else if ((var1 & 248) == 240) {
            var6 = 4;
         } else {
            var6 = -1;
         }

         var4[var1] = var6;
      }

      sInputCodesUTF8 = var4;
      var4 = new int[256];
      Arrays.fill(var4, -1);

      for(var3 = 33; var3 < 256; ++var3) {
         if (Character.isJavaIdentifierPart((char)var3)) {
            var4[var3] = 0;
         }
      }

      var4[64] = 0;
      var4[35] = 0;
      var4[42] = 0;
      var4[45] = 0;
      var4[43] = 0;
      sInputCodesJsNames = var4;
      var5 = new int[256];
      System.arraycopy(var4, 0, var5, 0, 256);
      Arrays.fill(var5, 128, 128, 0);
      sInputCodesUtf8JsNames = var5;
      var5 = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, var5, 128, 128);
      Arrays.fill(var5, 0, 32, -1);
      var5[9] = 0;
      var5[10] = 10;
      var5[13] = 13;
      var5[42] = 42;
      sInputCodesComment = var5;
      var5 = new int[256];
      System.arraycopy(sInputCodesUTF8, 128, var5, 128, 128);
      Arrays.fill(var5, 0, 32, -1);
      var5[32] = 1;
      var5[9] = 1;
      var5[10] = 10;
      var5[13] = 13;
      var5[47] = 47;
      var5[35] = 35;
      sInputCodesWS = var5;
      var5 = new int[128];

      for(var3 = 0; var3 < 32; ++var3) {
         var5[var3] = -1;
      }

      var5[34] = 34;
      var5[92] = 92;
      var5[8] = 98;
      var5[9] = 116;
      var5[12] = 102;
      var5[10] = 110;
      var5[13] = 114;
      sOutputEscapes128 = var5;
      var5 = new int[256];
      sHexValues = var5;
      Arrays.fill(var5, -1);
      var1 = 0;

      while(true) {
         var3 = var2;
         if (var1 >= 10) {
            while(var3 < 6) {
               var5 = sHexValues;
               var1 = var3 + 10;
               var5[var3 + 97] = var1;
               var5[var3 + 65] = var1;
               ++var3;
            }

            return;
         }

         sHexValues[var1 + 48] = var1++;
      }
   }

   public static void appendQuoted(StringBuilder var0, String var1) {
      int[] var2 = sOutputEscapes128;
      int var3 = var2.length;
      int var4 = var1.length();

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var1.charAt(var5);
         if (var6 < var3 && var2[var6] != 0) {
            var0.append('\\');
            int var7 = var2[var6];
            if (var7 < 0) {
               var0.append('u');
               var0.append('0');
               var0.append('0');
               var0.append(HC[var6 >> 4]);
               var0.append(HC[var6 & 15]);
            } else {
               var0.append((char)var7);
            }
         } else {
            var0.append(var6);
         }
      }

   }

   public static int charToHex(int var0) {
      return sHexValues[var0 & 255];
   }

   public static byte[] copyHexBytes() {
      return (byte[])HB.clone();
   }

   public static char[] copyHexChars() {
      return (char[])HC.clone();
   }

   public static int[] get7BitOutputEscapes() {
      return sOutputEscapes128;
   }

   public static int[] get7BitOutputEscapes(int var0) {
      return var0 == 34 ? sOutputEscapes128 : CharTypes.AltEscapes.instance.escapesFor(var0);
   }

   public static int[] getInputCodeComment() {
      return sInputCodesComment;
   }

   public static int[] getInputCodeLatin1() {
      return sInputCodes;
   }

   public static int[] getInputCodeLatin1JsNames() {
      return sInputCodesJsNames;
   }

   public static int[] getInputCodeUtf8() {
      return sInputCodesUTF8;
   }

   public static int[] getInputCodeUtf8JsNames() {
      return sInputCodesUtf8JsNames;
   }

   public static int[] getInputCodeWS() {
      return sInputCodesWS;
   }

   private static class AltEscapes {
      public static final CharTypes.AltEscapes instance = new CharTypes.AltEscapes();
      private int[][] _altEscapes = new int[128][];

      public int[] escapesFor(int var1) {
         int[] var2 = this._altEscapes[var1];
         int[] var3 = var2;
         if (var2 == null) {
            var3 = Arrays.copyOf(CharTypes.sOutputEscapes128, 128);
            if (var3[var1] == 0) {
               var3[var1] = -1;
            }

            this._altEscapes[var1] = var3;
         }

         return var3;
      }
   }
}
