package org.apache.http.impl.auth;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.util.EncodingUtils;

final class NTLMEngineImpl implements NTLMEngine {
   static final String DEFAULT_CHARSET = "ASCII";
   protected static final int FLAG_NEGOTIATE_128 = 536870912;
   protected static final int FLAG_NEGOTIATE_ALWAYS_SIGN = 32768;
   protected static final int FLAG_NEGOTIATE_KEY_EXCH = 1073741824;
   protected static final int FLAG_NEGOTIATE_NTLM = 512;
   protected static final int FLAG_NEGOTIATE_NTLM2 = 524288;
   protected static final int FLAG_NEGOTIATE_SEAL = 32;
   protected static final int FLAG_NEGOTIATE_SIGN = 16;
   protected static final int FLAG_TARGET_DESIRED = 4;
   protected static final int FLAG_UNICODE_ENCODING = 1;
   private static final SecureRandom RND_GEN;
   private static byte[] SIGNATURE;
   private String credentialCharset = "ASCII";

   static {
      SecureRandom var0;
      try {
         var0 = SecureRandom.getInstance("SHA1PRNG");
      } catch (Exception var2) {
         var0 = null;
      }

      RND_GEN = var0;
      byte[] var3 = EncodingUtils.getBytes("NTLMSSP", "ASCII");
      byte[] var1 = new byte[var3.length + 1];
      SIGNATURE = var1;
      System.arraycopy(var3, 0, var1, 0, var3.length);
      SIGNATURE[var3.length] = (byte)0;
   }

   static int F(int var0, int var1, int var2) {
      return var0 & var2 | var1 & var0;
   }

   static int G(int var0, int var1, int var2) {
      return var0 & var2 | var0 & var1 | var1 & var2;
   }

   static int H(int var0, int var1, int var2) {
      return var0 ^ var1 ^ var2;
   }

   private static String convertDomain(String var0) {
      return stripDotSuffix(var0);
   }

   private static String convertHost(String var0) {
      return stripDotSuffix(var0);
   }

   private static byte[] createBlob(byte[] var0, byte[] var1) {
      long var2 = (System.currentTimeMillis() + 11644473600000L) * 10000L;
      byte[] var4 = new byte[8];

      for(int var5 = 0; var5 < 8; ++var5) {
         var4[var5] = (byte)((byte)((int)var2));
         var2 >>>= 8;
      }

      byte[] var6 = new byte[var1.length + 28];
      System.arraycopy(new byte[]{1, 1, 0, 0}, 0, var6, 0, 4);
      System.arraycopy(new byte[]{0, 0, 0, 0}, 0, var6, 4, 4);
      System.arraycopy(var4, 0, var6, 8, 8);
      System.arraycopy(var0, 0, var6, 16, 8);
      System.arraycopy(new byte[]{0, 0, 0, 0}, 0, var6, 24, 4);
      System.arraycopy(var1, 0, var6, 28, var1.length);
      return var6;
   }

   private static Key createDESKey(byte[] var0, int var1) {
      byte[] var2 = new byte[7];
      System.arraycopy(var0, var1, var2, 0, 7);
      var0 = new byte[]{(byte)var2[0], (byte)((byte)(var2[0] << 7 | (var2[1] & 255) >>> 1)), (byte)((byte)(var2[1] << 6 | (var2[2] & 255) >>> 2)), (byte)((byte)(var2[2] << 5 | (var2[3] & 255) >>> 3)), (byte)((byte)(var2[3] << 4 | (var2[4] & 255) >>> 4)), (byte)((byte)(var2[4] << 3 | (var2[5] & 255) >>> 5)), (byte)((byte)(var2[5] << 2 | (var2[6] & 255) >>> 6)), (byte)((byte)(var2[6] << 1))};
      oddParity(var0);
      return new SecretKeySpec(var0, "DES");
   }

   static byte[] getLMResponse(String var0, byte[] var1) throws NTLMEngineException {
      return lmResponse(lmHash(var0), var1);
   }

   static byte[] getLMv2Response(String var0, String var1, String var2, byte[] var3, byte[] var4) throws NTLMEngineException {
      return lmv2Response(ntlmv2Hash(var0, var1, var2), var3, var4);
   }

   static byte[] getNTLM2SessionResponse(String var0, byte[] var1, byte[] var2) throws NTLMEngineException {
      try {
         byte[] var5 = ntlmHash(var0);
         MessageDigest var3 = MessageDigest.getInstance("MD5");
         var3.update(var1);
         var3.update(var2);
         var2 = var3.digest();
         var1 = new byte[8];
         System.arraycopy(var2, 0, var1, 0, 8);
         var5 = lmResponse(var5, var1);
         return var5;
      } catch (Exception var4) {
         if (var4 instanceof NTLMEngineException) {
            throw (NTLMEngineException)var4;
         } else {
            throw new NTLMEngineException(var4.getMessage(), var4);
         }
      }
   }

   static byte[] getNTLMResponse(String var0, byte[] var1) throws NTLMEngineException {
      return lmResponse(ntlmHash(var0), var1);
   }

   static byte[] getNTLMv2Response(String var0, String var1, String var2, byte[] var3, byte[] var4, byte[] var5) throws NTLMEngineException {
      return lmv2Response(ntlmv2Hash(var0, var1, var2), var3, createBlob(var4, var5));
   }

   private static byte[] lmHash(String var0) throws NTLMEngineException {
      try {
         byte[] var6 = var0.toUpperCase().getBytes("US-ASCII");
         int var1 = Math.min(var6.length, 14);
         byte[] var2 = new byte[14];
         System.arraycopy(var6, 0, var2, 0, var1);
         Key var7 = createDESKey(var2, 0);
         Key var8 = createDESKey(var2, 7);
         byte[] var3 = "KGS!@#$%".getBytes("US-ASCII");
         Cipher var4 = Cipher.getInstance("DES/ECB/NoPadding");
         var4.init(1, var7);
         var6 = var4.doFinal(var3);
         var4.init(1, var8);
         var3 = var4.doFinal(var3);
         var2 = new byte[16];
         System.arraycopy(var6, 0, var2, 0, 8);
         System.arraycopy(var3, 0, var2, 8, 8);
         return var2;
      } catch (Exception var5) {
         throw new NTLMEngineException(var5.getMessage(), var5);
      }
   }

   private static byte[] lmResponse(byte[] var0, byte[] var1) throws NTLMEngineException {
      try {
         byte[] var2 = new byte[21];
         System.arraycopy(var0, 0, var2, 0, 16);
         Key var6 = createDESKey(var2, 0);
         Key var3 = createDESKey(var2, 7);
         Key var7 = createDESKey(var2, 14);
         Cipher var4 = Cipher.getInstance("DES/ECB/NoPadding");
         var4.init(1, var6);
         var0 = var4.doFinal(var1);
         var4.init(1, var3);
         byte[] var8 = var4.doFinal(var1);
         var4.init(1, var7);
         var2 = var4.doFinal(var1);
         var1 = new byte[24];
         System.arraycopy(var0, 0, var1, 0, 8);
         System.arraycopy(var8, 0, var1, 8, 8);
         System.arraycopy(var2, 0, var1, 16, 8);
         return var1;
      } catch (Exception var5) {
         throw new NTLMEngineException(var5.getMessage(), var5);
      }
   }

   private static byte[] lmv2Response(byte[] var0, byte[] var1, byte[] var2) throws NTLMEngineException {
      NTLMEngineImpl.HMACMD5 var3 = new NTLMEngineImpl.HMACMD5(var0);
      var3.update(var1);
      var3.update(var2);
      var1 = var3.getOutput();
      var0 = new byte[var1.length + var2.length];
      System.arraycopy(var1, 0, var0, 0, var1.length);
      System.arraycopy(var2, 0, var0, var1.length, var2.length);
      return var0;
   }

   private static byte[] makeNTLM2RandomChallenge() throws NTLMEngineException {
      // $FF: Couldn't be decompiled
   }

   private static byte[] makeRandomChallenge() throws NTLMEngineException {
      // $FF: Couldn't be decompiled
   }

   private static byte[] ntlmHash(String var0) throws NTLMEngineException {
      try {
         byte[] var1 = var0.getBytes("UnicodeLittleUnmarked");
         NTLMEngineImpl.MD4 var4 = new NTLMEngineImpl.MD4();
         var4.update(var1);
         byte[] var5 = var4.getOutput();
         return var5;
      } catch (UnsupportedEncodingException var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append("Unicode not supported: ");
         var3.append(var2.getMessage());
         throw new NTLMEngineException(var3.toString(), var2);
      }
   }

   private static byte[] ntlmv2Hash(String var0, String var1, String var2) throws NTLMEngineException {
      try {
         byte[] var3 = ntlmHash(var2);
         NTLMEngineImpl.HMACMD5 var7 = new NTLMEngineImpl.HMACMD5(var3);
         var7.update(var1.toUpperCase().getBytes("UnicodeLittleUnmarked"));
         var7.update(var0.getBytes("UnicodeLittleUnmarked"));
         byte[] var5 = var7.getOutput();
         return var5;
      } catch (UnsupportedEncodingException var4) {
         StringBuilder var6 = new StringBuilder();
         var6.append("Unicode not supported! ");
         var6.append(var4.getMessage());
         throw new NTLMEngineException(var6.toString(), var4);
      }
   }

   private static void oddParity(byte[] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         byte var2 = var0[var1];
         boolean var3;
         if (((var2 >>> 1 ^ var2 >>> 7 ^ var2 >>> 6 ^ var2 >>> 5 ^ var2 >>> 4 ^ var2 >>> 3 ^ var2 >>> 2) & 1) == 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         if (var3) {
            var0[var1] = (byte)((byte)(var0[var1] | 1));
         } else {
            var0[var1] = (byte)((byte)(var0[var1] & -2));
         }
      }

   }

   private static byte[] readSecurityBuffer(byte[] var0, int var1) throws NTLMEngineException {
      int var2 = readUShort(var0, var1);
      var1 = readULong(var0, var1 + 4);
      if (var0.length >= var1 + var2) {
         byte[] var3 = new byte[var2];
         System.arraycopy(var0, var1, var3, 0, var2);
         return var3;
      } else {
         throw new NTLMEngineException("NTLM authentication - buffer too small for data item");
      }
   }

   private static int readULong(byte[] var0, int var1) throws NTLMEngineException {
      if (var0.length >= var1 + 4) {
         byte var2 = var0[var1];
         byte var3 = var0[var1 + 1];
         byte var4 = var0[var1 + 2];
         return (var0[var1 + 3] & 255) << 24 | var2 & 255 | (var3 & 255) << 8 | (var4 & 255) << 16;
      } else {
         throw new NTLMEngineException("NTLM authentication - buffer too small for DWORD");
      }
   }

   private static int readUShort(byte[] var0, int var1) throws NTLMEngineException {
      if (var0.length >= var1 + 2) {
         byte var2 = var0[var1];
         return (var0[var1 + 1] & 255) << 8 | var2 & 255;
      } else {
         throw new NTLMEngineException("NTLM authentication - buffer too small for WORD");
      }
   }

   static int rotintlft(int var0, int var1) {
      return var0 >>> 32 - var1 | var0 << var1;
   }

   private static String stripDotSuffix(String var0) {
      int var1 = var0.indexOf(".");
      String var2 = var0;
      if (var1 != -1) {
         var2 = var0.substring(0, var1);
      }

      return var2;
   }

   static void writeULong(byte[] var0, int var1, int var2) {
      var0[var2] = (byte)((byte)(var1 & 255));
      var0[var2 + 1] = (byte)((byte)(var1 >> 8 & 255));
      var0[var2 + 2] = (byte)((byte)(var1 >> 16 & 255));
      var0[var2 + 3] = (byte)((byte)(var1 >> 24 & 255));
   }

   public String generateType1Msg(String var1, String var2) throws NTLMEngineException {
      return this.getType1Message(var2, var1);
   }

   public String generateType3Msg(String var1, String var2, String var3, String var4, String var5) throws NTLMEngineException {
      NTLMEngineImpl.Type2Message var6 = new NTLMEngineImpl.Type2Message(var5);
      return this.getType3Message(var1, var2, var4, var3, var6.getChallenge(), var6.getFlags(), var6.getTarget(), var6.getTargetInfo());
   }

   String getCredentialCharset() {
      return this.credentialCharset;
   }

   final String getResponseFor(String var1, String var2, String var3, String var4, String var5) throws NTLMEngineException {
      if (var1 != null && !var1.trim().equals("")) {
         NTLMEngineImpl.Type2Message var6 = new NTLMEngineImpl.Type2Message(var1);
         var1 = this.getType3Message(var2, var3, var4, var5, var6.getChallenge(), var6.getFlags(), var6.getTarget(), var6.getTargetInfo());
      } else {
         var1 = this.getType1Message(var4, var5);
      }

      return var1;
   }

   String getType1Message(String var1, String var2) throws NTLMEngineException {
      return (new NTLMEngineImpl.Type1Message(var2, var1)).getResponse();
   }

   String getType3Message(String var1, String var2, String var3, String var4, byte[] var5, int var6, String var7, byte[] var8) throws NTLMEngineException {
      return (new NTLMEngineImpl.Type3Message(var4, var3, var1, var2, var5, var6, var7, var8)).getResponse();
   }

   void setCredentialCharset(String var1) {
      this.credentialCharset = var1;
   }

   static class HMACMD5 {
      protected byte[] ipad;
      protected MessageDigest md5;
      protected byte[] opad;

      HMACMD5(byte[] var1) throws NTLMEngineException {
         MessageDigest var2;
         try {
            var2 = MessageDigest.getInstance("MD5");
            this.md5 = var2;
         } catch (Exception var7) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Error getting md5 message digest implementation: ");
            var8.append(var7.getMessage());
            throw new NTLMEngineException(var8.toString(), var7);
         }

         this.ipad = new byte[64];
         this.opad = new byte[64];
         int var3 = var1.length;
         int var4 = var3;
         byte[] var5 = var1;
         if (var3 > 64) {
            var2.update(var1);
            var5 = this.md5.digest();
            var4 = var5.length;
         }

         var3 = 0;

         while(true) {
            int var6 = var3;
            if (var3 >= var4) {
               while(var6 < 64) {
                  this.ipad[var6] = (byte)54;
                  this.opad[var6] = (byte)92;
                  ++var6;
               }

               this.md5.reset();
               this.md5.update(this.ipad);
               return;
            }

            this.ipad[var3] = (byte)((byte)(54 ^ var5[var3]));
            this.opad[var3] = (byte)((byte)(92 ^ var5[var3]));
            ++var3;
         }
      }

      byte[] getOutput() {
         byte[] var1 = this.md5.digest();
         this.md5.update(this.opad);
         return this.md5.digest(var1);
      }

      void update(byte[] var1) {
         this.md5.update(var1);
      }

      void update(byte[] var1, int var2, int var3) {
         this.md5.update(var1, var2, var3);
      }
   }

   static class MD4 {
      protected int A = 1732584193;
      protected int B = -271733879;
      protected int C = -1732584194;
      protected int D = 271733878;
      protected long count = 0L;
      protected byte[] dataBuffer = new byte[64];

      byte[] getOutput() {
         int var1 = (int)(this.count & 63L);
         if (var1 < 56) {
            var1 = 56 - var1;
         } else {
            var1 = 120 - var1;
         }

         byte[] var2 = new byte[var1 + 8];
         var2[0] = (byte)-128;

         for(int var3 = 0; var3 < 8; ++var3) {
            var2[var1 + var3] = (byte)((byte)((int)(this.count * 8L >>> var3 * 8)));
         }

         this.update(var2);
         var2 = new byte[16];
         NTLMEngineImpl.writeULong(var2, this.A, 0);
         NTLMEngineImpl.writeULong(var2, this.B, 4);
         NTLMEngineImpl.writeULong(var2, this.C, 8);
         NTLMEngineImpl.writeULong(var2, this.D, 12);
         return var2;
      }

      protected void processBuffer() {
         int[] var1 = new int[16];

         int var2;
         int var4;
         for(var2 = 0; var2 < 16; ++var2) {
            byte[] var3 = this.dataBuffer;
            var4 = var2 * 4;
            var1[var2] = (var3[var4] & 255) + ((var3[var4 + 1] & 255) << 8) + ((var3[var4 + 2] & 255) << 16) + ((var3[var4 + 3] & 255) << 24);
         }

         var2 = this.A;
         int var5 = this.B;
         int var6 = this.C;
         var4 = this.D;
         this.round1(var1);
         this.round2(var1);
         this.round3(var1);
         this.A += var2;
         this.B += var5;
         this.C += var6;
         this.D += var4;
      }

      protected void round1(int[] var1) {
         int var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + var1[0], 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(var2, this.B, this.C) + var1[1], 7);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(var2, this.A, this.B) + var1[2], 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(var2, this.D, this.A) + var1[3], 19);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(var2, this.C, this.D) + var1[4], 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(var2, this.B, this.C) + var1[5], 7);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(var2, this.A, this.B) + var1[6], 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(var2, this.D, this.A) + var1[7], 19);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(var2, this.C, this.D) + var1[8], 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(var2, this.B, this.C) + var1[9], 7);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(var2, this.A, this.B) + var1[10], 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(var2, this.D, this.A) + var1[11], 19);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(var2, this.C, this.D) + var1[12], 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(var2, this.B, this.C) + var1[13], 7);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(var2, this.A, this.B) + var1[14], 11);
         this.C = var2;
         this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(var2, this.D, this.A) + var1[15], 19);
      }

      protected void round2(int[] var1) {
         int var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + var1[0] + 1518500249, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(var2, this.B, this.C) + var1[4] + 1518500249, 5);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(var2, this.A, this.B) + var1[8] + 1518500249, 9);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(var2, this.D, this.A) + var1[12] + 1518500249, 13);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(var2, this.C, this.D) + var1[1] + 1518500249, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(var2, this.B, this.C) + var1[5] + 1518500249, 5);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(var2, this.A, this.B) + var1[9] + 1518500249, 9);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(var2, this.D, this.A) + var1[13] + 1518500249, 13);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(var2, this.C, this.D) + var1[2] + 1518500249, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(var2, this.B, this.C) + var1[6] + 1518500249, 5);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(var2, this.A, this.B) + var1[10] + 1518500249, 9);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(var2, this.D, this.A) + var1[14] + 1518500249, 13);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(var2, this.C, this.D) + var1[3] + 1518500249, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(var2, this.B, this.C) + var1[7] + 1518500249, 5);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(var2, this.A, this.B) + var1[11] + 1518500249, 9);
         this.C = var2;
         this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(var2, this.D, this.A) + var1[15] + 1518500249, 13);
      }

      protected void round3(int[] var1) {
         int var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + var1[0] + 1859775393, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(var2, this.B, this.C) + var1[8] + 1859775393, 9);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(var2, this.A, this.B) + var1[4] + 1859775393, 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(var2, this.D, this.A) + var1[12] + 1859775393, 15);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(var2, this.C, this.D) + var1[2] + 1859775393, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(var2, this.B, this.C) + var1[10] + 1859775393, 9);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(var2, this.A, this.B) + var1[6] + 1859775393, 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(var2, this.D, this.A) + var1[14] + 1859775393, 15);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(var2, this.C, this.D) + var1[1] + 1859775393, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(var2, this.B, this.C) + var1[9] + 1859775393, 9);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(var2, this.A, this.B) + var1[5] + 1859775393, 11);
         this.C = var2;
         var2 = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(var2, this.D, this.A) + var1[13] + 1859775393, 15);
         this.B = var2;
         var2 = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(var2, this.C, this.D) + var1[3] + 1859775393, 3);
         this.A = var2;
         var2 = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(var2, this.B, this.C) + var1[11] + 1859775393, 9);
         this.D = var2;
         var2 = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(var2, this.A, this.B) + var1[7] + 1859775393, 11);
         this.C = var2;
         this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(var2, this.D, this.A) + var1[15] + 1859775393, 15);
      }

      void update(byte[] var1) {
         int var2 = (int)(this.count & 63L);
         int var3 = 0;

         while(true) {
            int var4 = var1.length;
            byte[] var5 = this.dataBuffer;
            if (var4 - var3 + var2 < var5.length) {
               if (var3 < var1.length) {
                  var4 = var1.length - var3;
                  System.arraycopy(var1, var3, var5, var2, var4);
                  this.count += (long)var4;
               }

               return;
            }

            var4 = var5.length - var2;
            System.arraycopy(var1, var3, var5, var2, var4);
            this.count += (long)var4;
            var3 += var4;
            this.processBuffer();
            var2 = 0;
         }
      }
   }

   static class NTLMMessage {
      private int currentOutputPosition;
      private byte[] messageContents = null;

      NTLMMessage() {
         this.currentOutputPosition = 0;
      }

      NTLMMessage(String var1, int var2) throws NTLMEngineException {
         int var3 = 0;
         this.currentOutputPosition = 0;
         byte[] var4 = Base64.decodeBase64(EncodingUtils.getBytes(var1, "ASCII"));
         this.messageContents = var4;
         if (var4.length >= NTLMEngineImpl.SIGNATURE.length) {
            while(var3 < NTLMEngineImpl.SIGNATURE.length) {
               if (this.messageContents[var3] != NTLMEngineImpl.SIGNATURE[var3]) {
                  throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
               }

               ++var3;
            }

            var3 = this.readULong(NTLMEngineImpl.SIGNATURE.length);
            if (var3 == var2) {
               this.currentOutputPosition = this.messageContents.length;
            } else {
               StringBuilder var5 = new StringBuilder();
               var5.append("NTLM type ");
               var5.append(Integer.toString(var2));
               var5.append(" message expected - instead got type ");
               var5.append(Integer.toString(var3));
               throw new NTLMEngineException(var5.toString());
            }
         } else {
            throw new NTLMEngineException("NTLM message decoding error - packet too short");
         }
      }

      protected void addByte(byte var1) {
         byte[] var2 = this.messageContents;
         int var3 = this.currentOutputPosition;
         var2[var3] = (byte)var1;
         this.currentOutputPosition = var3 + 1;
      }

      protected void addBytes(byte[] var1) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            byte[] var3 = this.messageContents;
            int var4 = this.currentOutputPosition;
            var3[var4] = (byte)var1[var2];
            this.currentOutputPosition = var4 + 1;
         }

      }

      protected void addULong(int var1) {
         this.addByte((byte)(var1 & 255));
         this.addByte((byte)(var1 >> 8 & 255));
         this.addByte((byte)(var1 >> 16 & 255));
         this.addByte((byte)(var1 >> 24 & 255));
      }

      protected void addUShort(int var1) {
         this.addByte((byte)(var1 & 255));
         this.addByte((byte)(var1 >> 8 & 255));
      }

      protected int getMessageLength() {
         return this.currentOutputPosition;
      }

      protected int getPreambleLength() {
         return NTLMEngineImpl.SIGNATURE.length + 4;
      }

      String getResponse() {
         byte[] var1 = this.messageContents;
         int var2 = var1.length;
         int var3 = this.currentOutputPosition;
         if (var2 > var3) {
            byte[] var4 = new byte[var3];
            var2 = 0;

            while(true) {
               var1 = var4;
               if (var2 >= this.currentOutputPosition) {
                  break;
               }

               var4[var2] = (byte)this.messageContents[var2];
               ++var2;
            }
         }

         return EncodingUtils.getAsciiString(Base64.encodeBase64(var1));
      }

      protected void prepareResponse(int var1, int var2) {
         this.messageContents = new byte[var1];
         this.currentOutputPosition = 0;
         this.addBytes(NTLMEngineImpl.SIGNATURE);
         this.addULong(var2);
      }

      protected byte readByte(int var1) throws NTLMEngineException {
         byte[] var2 = this.messageContents;
         if (var2.length >= var1 + 1) {
            return var2[var1];
         } else {
            throw new NTLMEngineException("NTLM: Message too short");
         }
      }

      protected void readBytes(byte[] var1, int var2) throws NTLMEngineException {
         byte[] var3 = this.messageContents;
         if (var3.length >= var1.length + var2) {
            System.arraycopy(var3, var2, var1, 0, var1.length);
         } else {
            throw new NTLMEngineException("NTLM: Message too short");
         }
      }

      protected byte[] readSecurityBuffer(int var1) throws NTLMEngineException {
         return NTLMEngineImpl.readSecurityBuffer(this.messageContents, var1);
      }

      protected int readULong(int var1) throws NTLMEngineException {
         return NTLMEngineImpl.readULong(this.messageContents, var1);
      }

      protected int readUShort(int var1) throws NTLMEngineException {
         return NTLMEngineImpl.readUShort(this.messageContents, var1);
      }
   }

   static class Type1Message extends NTLMEngineImpl.NTLMMessage {
      protected byte[] domainBytes;
      protected byte[] hostBytes;

      Type1Message(String var1, String var2) throws NTLMEngineException {
         try {
            var2 = NTLMEngineImpl.stripDotSuffix(var2);
            var1 = NTLMEngineImpl.stripDotSuffix(var1);
            this.hostBytes = var2.getBytes("UnicodeLittleUnmarked");
            this.domainBytes = var1.toUpperCase().getBytes("UnicodeLittleUnmarked");
         } catch (UnsupportedEncodingException var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append("Unicode unsupported: ");
            var4.append(var3.getMessage());
            throw new NTLMEngineException(var4.toString(), var3);
         }
      }

      String getResponse() {
         this.prepareResponse(this.hostBytes.length + 32 + this.domainBytes.length, 1);
         this.addULong(537395765);
         this.addUShort(this.domainBytes.length);
         this.addUShort(this.domainBytes.length);
         this.addULong(this.hostBytes.length + 32);
         this.addUShort(this.hostBytes.length);
         this.addUShort(this.hostBytes.length);
         this.addULong(32);
         this.addBytes(this.hostBytes);
         this.addBytes(this.domainBytes);
         return super.getResponse();
      }
   }

   static class Type2Message extends NTLMEngineImpl.NTLMMessage {
      protected byte[] challenge;
      protected int flags;
      protected String target;
      protected byte[] targetInfo;

      Type2Message(String var1) throws NTLMEngineException {
         super(var1, 2);
         byte[] var5 = new byte[8];
         this.challenge = var5;
         this.readBytes(var5, 24);
         int var2 = this.readULong(20);
         this.flags = var2;
         if ((var2 & 1) != 0) {
            this.target = null;
            if (this.getMessageLength() >= 20) {
               var5 = this.readSecurityBuffer(12);
               if (var5.length != 0) {
                  try {
                     String var3 = new String(var5, "UnicodeLittleUnmarked");
                     this.target = var3;
                  } catch (UnsupportedEncodingException var4) {
                     throw new NTLMEngineException(var4.getMessage(), var4);
                  }
               }
            }

            this.targetInfo = null;
            if (this.getMessageLength() >= 48) {
               var5 = this.readSecurityBuffer(40);
               if (var5.length != 0) {
                  this.targetInfo = var5;
               }
            }

         } else {
            StringBuilder var6 = new StringBuilder();
            var6.append("NTLM type 2 message has flags that make no sense: ");
            var6.append(Integer.toString(this.flags));
            throw new NTLMEngineException(var6.toString());
         }
      }

      byte[] getChallenge() {
         return this.challenge;
      }

      int getFlags() {
         return this.flags;
      }

      String getTarget() {
         return this.target;
      }

      byte[] getTargetInfo() {
         return this.targetInfo;
      }
   }

   static class Type3Message extends NTLMEngineImpl.NTLMMessage {
      protected byte[] domainBytes;
      protected byte[] hostBytes;
      protected byte[] lmResp;
      protected byte[] ntResp;
      protected int type2Flags;
      protected byte[] userBytes;

      Type3Message(String var1, String var2, String var3, String var4, byte[] var5, int var6, String var7, byte[] var8) throws NTLMEngineException {
         label40: {
            super();
            this.type2Flags = var6;
            var2 = NTLMEngineImpl.stripDotSuffix(var2);
            var1 = NTLMEngineImpl.stripDotSuffix(var1);
            boolean var10001;
            if (var8 != null && var7 != null) {
               try {
                  byte[] var9 = NTLMEngineImpl.makeRandomChallenge();
                  this.ntResp = NTLMEngineImpl.getNTLMv2Response(var7, var3, var4, var5, var9, var8);
                  this.lmResp = NTLMEngineImpl.getLMv2Response(var7, var3, var4, var5, var9);
                  break label40;
               } catch (NTLMEngineException var11) {
                  var10001 = false;
               }
            } else if ((var6 & 524288) != 0) {
               try {
                  byte[] var15 = NTLMEngineImpl.makeNTLM2RandomChallenge();
                  this.ntResp = NTLMEngineImpl.getNTLM2SessionResponse(var4, var5, var15);
                  this.lmResp = var15;
                  break label40;
               } catch (NTLMEngineException var12) {
                  var10001 = false;
               }
            } else {
               try {
                  this.ntResp = NTLMEngineImpl.getNTLMResponse(var4, var5);
                  this.lmResp = NTLMEngineImpl.getLMResponse(var4, var5);
                  break label40;
               } catch (NTLMEngineException var13) {
                  var10001 = false;
               }
            }

            this.ntResp = new byte[0];
            this.lmResp = NTLMEngineImpl.getLMResponse(var4, var5);
         }

         try {
            this.domainBytes = var1.toUpperCase().getBytes("UnicodeLittleUnmarked");
            this.hostBytes = var2.getBytes("UnicodeLittleUnmarked");
            this.userBytes = var3.getBytes("UnicodeLittleUnmarked");
         } catch (UnsupportedEncodingException var10) {
            StringBuilder var14 = new StringBuilder();
            var14.append("Unicode not supported: ");
            var14.append(var10.getMessage());
            throw new NTLMEngineException(var14.toString(), var10);
         }
      }

      String getResponse() {
         int var1 = this.ntResp.length;
         int var2 = this.lmResp.length;
         int var3 = this.domainBytes.length;
         int var4 = this.hostBytes.length;
         int var5 = this.userBytes.length;
         int var6 = var2 + 64;
         int var7 = var6 + var1;
         int var8 = var7 + var3;
         int var9 = var8 + var5;
         int var10 = var9 + var4 + 0;
         this.prepareResponse(var10, 3);
         this.addUShort(var2);
         this.addUShort(var2);
         this.addULong(64);
         this.addUShort(var1);
         this.addUShort(var1);
         this.addULong(var6);
         this.addUShort(var3);
         this.addUShort(var3);
         this.addULong(var7);
         this.addUShort(var5);
         this.addUShort(var5);
         this.addULong(var8);
         this.addUShort(var4);
         this.addUShort(var4);
         this.addULong(var9);
         this.addULong(0);
         this.addULong(var10);
         var4 = this.type2Flags;
         this.addULong(var4 & '耀' | 524288 & var4 | 536871429 | var4 & 16 | var4 & 32 | 1073741824 & var4);
         this.addBytes(this.lmResp);
         this.addBytes(this.ntResp);
         this.addBytes(this.domainBytes);
         this.addBytes(this.userBytes);
         this.addBytes(this.hostBytes);
         return super.getResponse();
      }
   }
}
