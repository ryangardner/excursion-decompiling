package com.syntak.library;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherOp {
   public static final String DECRIPTION = "decription";
   public static final String ENCRIPTION = "encription";
   static final int MAX_DISTANCE = 240;
   static final int MIN_CIPHER_DATA_LENGTH = 256;
   static final int MIN_DISTANCE = 20;
   static final int OBSTACLE_LENGTH = 16;
   private static final String TAG_MD5 = "MD5";
   static int iterationCount = 19;
   static AlgorithmParameterSpec paramSpec;
   static byte[] salt = new byte[]{-87, -101, -56, 50, 86, 52, -29, 3};

   public static void createMapFiles(String var0, String var1) {
      byte[] var2 = new byte[256];
      byte[] var3 = new byte[256];
      boolean[] var4 = new boolean[256];

      int var5;
      for(var5 = 0; var5 < 256; ++var5) {
         var4[var5] = false;
      }

      for(var5 = 0; var5 < 255; ++var5) {
         int var6 = MathOp.randomInteger(0, 255 - var5);
         int var7 = 0;

         int var9;
         for(int var8 = 0; var7 < 256; var8 = var9) {
            var9 = var8;
            if (!var4[var7]) {
               if (var8 >= var6) {
                  break;
               }

               var9 = var8 + 1;
            }

            ++var7;
         }

         var4[var7] = true;
         var2[var5] = (byte)((byte)var7);
         var3[var7] = (byte)((byte)var5);
      }

      for(var5 = 0; var5 < 256; ++var5) {
         if (!var4[var5]) {
            var3[var5] = (byte)-1;
            var2[255] = (byte)((byte)var5);
         }
      }

      FileOp.deleteFile(var0);
      FileOp.deleteFile(var1);

      try {
         FileOutputStream var12 = new FileOutputStream(var0);
         FileOutputStream var11 = new FileOutputStream(var1);
         var12.write(var2, 0, 256);
         var11.write(var3, 0, 256);
         var12.flush();
         var12.close();
         var11.flush();
         var11.close();
      } catch (IOException | FileNotFoundException var10) {
      }

   }

   public static byte[] decriptData(SecretKey var0, byte[] var1) throws Exception {
      Cipher var2 = Cipher.getInstance(var0.getAlgorithm());
      var2.init(2, var0, paramSpec);
      return var2.doFinal(var1);
   }

   public static byte[] decriptData(byte[] var0, int var1, CipherOp.CipherParameter var2) {
      if (var1 < var2.minCipherDataLength) {
         return var0;
      } else {
         byte var3 = var0[var2.obstacleLength - 1];
         int var4 = var3;
         if (var3 < 0) {
            var4 = var3 + 256;
         }

         var1 = var1 - var2.obstacleLength * 2 - var4;
         byte[] var5 = new byte[var1];
         int var6 = var2.obstacleLength;
         System.arraycopy(var0, var6, var5, 0, var4);
         System.arraycopy(var0, var6 + var2.obstacleLength + var4, var5, var4 + 0, var1 - var4);
         return var5;
      }
   }

   public static byte[] decriptData(byte[] var0, byte[] var1) throws Exception {
      SecretKeySpec var3 = new SecretKeySpec(var0, "AES");
      Cipher var2 = Cipher.getInstance("AES");
      var2.init(2, var3);
      return var2.doFinal(var1);
   }

   public static byte[] encriptData(SecretKey var0, byte[] var1) throws Exception {
      Cipher var2 = Cipher.getInstance(var0.getAlgorithm());
      var2.init(1, var0, paramSpec);
      return var2.doFinal(var1);
   }

   public static byte[] encriptData(byte[] var0, int var1, CipherOp.CipherParameter var2) {
      if (var1 < var2.minCipherDataLength) {
         return var0;
      } else {
         int var3 = var2.obstacleLength;
         byte[] var4 = new byte[var3];
         int var5 = var2.obstacleLength;
         byte[] var6 = new byte[var5];

         int var7;
         for(var7 = 0; var7 < var2.obstacleLength; ++var7) {
            var4[var7] = (byte)((byte)MathOp.randomInteger(0, 255));
            var6[var7] = (byte)((byte)MathOp.randomInteger(0, 255));
         }

         var7 = MathOp.randomInteger(var2.minObstacleDistance, var2.maxObstacleDistance);
         var4[var2.obstacleLength - 1] = (byte)((byte)var7);
         byte[] var8 = new byte[var2.obstacleLength * 2 + var1 + var7];
         System.arraycopy(var4, 0, var8, 0, var3);
         var3 += 0;
         System.arraycopy(var0, 0, var8, var3, var7);
         var3 += var7;
         System.arraycopy(var6, 0, var8, var3, var5);
         System.arraycopy(var0, var7, var8, var3 + var5, var1 - var7);
         return var8;
      }
   }

   public static byte[] encriptData(byte[] var0, byte[] var1) throws Exception {
      SecretKeySpec var2 = new SecretKeySpec(var0, "AES");
      Cipher var3 = Cipher.getInstance("AES");
      var3.init(1, var2);
      return var3.doFinal(var1);
   }

   public static SecretKey generateKey(String var0) throws Exception {
      PBEKeySpec var1 = new PBEKeySpec(var0.toCharArray(), salt, iterationCount);
      SecretKey var2 = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(var1);
      paramSpec = new PBEParameterSpec(salt, iterationCount);
      return var2;
   }

   public static byte[] mappingData(byte[] var0, byte[] var1, int var2) {
      byte[] var3 = new byte[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         byte var5 = var1[var4];
         int var6 = var5;
         if (var5 < 0) {
            var6 = var5 + 256;
         }

         var3[var4] = (byte)var0[var6];
      }

      return var3;
   }

   public static byte[] readMapFile(String var0) {
      if (var0 == null) {
         return null;
      } else if (!FileOp.checkFileExist(var0)) {
         return null;
      } else {
         byte[] var1 = new byte[256];

         try {
            FileInputStream var2 = new FileInputStream(var0);
            var2.read(var1, 0, 256);
         } catch (FileNotFoundException var3) {
            return null;
         } catch (IOException var4) {
         }

         return var1;
      }
   }

   public boolean checkMD5(String var1, File var2) {
      if (!TextUtils.isEmpty(var1) && var2 != null) {
         String var4 = this.getMD5(var2);
         if (var4 == null) {
            Log.e("MD5", "calculatedMD5 null");
            return false;
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Calculated MD5: ");
            var3.append(var4);
            Log.v("MD5", var3.toString());
            var3 = new StringBuilder();
            var3.append("Provided MD5: ");
            var3.append(var1);
            Log.v("MD5", var3.toString());
            return var4.equalsIgnoreCase(var1);
         }
      } else {
         Log.e("MD5", "MD5 string empty or updateFile null");
         return false;
      }
   }

   public String getMD5(File param1) {
      // $FF: Couldn't be decompiled
   }

   public static class CipherParameter {
      public int maxObstacleDistance = 240;
      public int minCipherDataLength = 256;
      public int minObstacleDistance = 20;
      public int obstacleLength = 16;
   }
}
