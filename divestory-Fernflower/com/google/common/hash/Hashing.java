package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.crypto.spec.SecretKeySpec;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Hashing {
   static final int GOOD_FAST_HASH_SEED = (int)System.currentTimeMillis();

   private Hashing() {
   }

   public static HashFunction adler32() {
      return Hashing.ChecksumType.ADLER_32.hashFunction;
   }

   static int checkPositiveAndMakeMultipleOf32(int var0) {
      boolean var1;
      if (var0 > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      Preconditions.checkArgument(var1, "Number of bits must be positive");
      return var0 + 31 & -32;
   }

   public static HashCode combineOrdered(Iterable<HashCode> var0) {
      Iterator var1 = var0.iterator();
      Preconditions.checkArgument(var1.hasNext(), "Must be at least 1 hash code to combine.");
      int var2 = ((HashCode)var1.next()).bits() / 8;
      byte[] var8 = new byte[var2];
      Iterator var7 = var0.iterator();

      while(var7.hasNext()) {
         byte[] var3 = ((HashCode)var7.next()).asBytes();
         int var4 = var3.length;
         int var5 = 0;
         boolean var6;
         if (var4 == var2) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "All hashcodes must have the same bit length.");

         while(var5 < var3.length) {
            var8[var5] = (byte)((byte)(var8[var5] * 37 ^ var3[var5]));
            ++var5;
         }
      }

      return HashCode.fromBytesNoCopy(var8);
   }

   public static HashCode combineUnordered(Iterable<HashCode> var0) {
      Iterator var1 = var0.iterator();
      Preconditions.checkArgument(var1.hasNext(), "Must be at least 1 hash code to combine.");
      int var2 = ((HashCode)var1.next()).bits() / 8;
      byte[] var8 = new byte[var2];
      Iterator var7 = var0.iterator();

      while(var7.hasNext()) {
         byte[] var3 = ((HashCode)var7.next()).asBytes();
         int var4 = var3.length;
         int var5 = 0;
         boolean var6;
         if (var4 == var2) {
            var6 = true;
         } else {
            var6 = false;
         }

         Preconditions.checkArgument(var6, "All hashcodes must have the same bit length.");

         while(var5 < var3.length) {
            var8[var5] = (byte)((byte)(var8[var5] + var3[var5]));
            ++var5;
         }
      }

      return HashCode.fromBytesNoCopy(var8);
   }

   public static HashFunction concatenating(HashFunction var0, HashFunction var1, HashFunction... var2) {
      ArrayList var3 = new ArrayList();
      var3.add(var0);
      var3.add(var1);
      var3.addAll(Arrays.asList(var2));
      return new Hashing.ConcatenatedHashFunction((HashFunction[])var3.toArray(new HashFunction[0]));
   }

   public static HashFunction concatenating(Iterable<HashFunction> var0) {
      Preconditions.checkNotNull(var0);
      ArrayList var1 = new ArrayList();
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         var1.add((HashFunction)var3.next());
      }

      boolean var2;
      if (var1.size() > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "number of hash functions (%s) must be > 0", var1.size());
      return new Hashing.ConcatenatedHashFunction((HashFunction[])var1.toArray(new HashFunction[0]));
   }

   public static int consistentHash(long var0, int var2) {
      int var3 = 0;
      boolean var4;
      if (var2 > 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "buckets must be positive: %s", var2);
      Hashing.LinearCongruentialGenerator var5 = new Hashing.LinearCongruentialGenerator(var0);

      while(true) {
         int var6 = (int)((double)(var3 + 1) / var5.nextDouble());
         if (var6 < 0 || var6 >= var2) {
            return var3;
         }

         var3 = var6;
      }
   }

   public static int consistentHash(HashCode var0, int var1) {
      return consistentHash(var0.padToLong(), var1);
   }

   public static HashFunction crc32() {
      return Hashing.ChecksumType.CRC_32.hashFunction;
   }

   public static HashFunction crc32c() {
      return Crc32cHashFunction.CRC_32_C;
   }

   public static HashFunction farmHashFingerprint64() {
      return FarmHashFingerprint64.FARMHASH_FINGERPRINT_64;
   }

   public static HashFunction goodFastHash(int var0) {
      var0 = checkPositiveAndMakeMultipleOf32(var0);
      if (var0 == 32) {
         return Murmur3_32HashFunction.GOOD_FAST_HASH_32;
      } else if (var0 <= 128) {
         return Murmur3_128HashFunction.GOOD_FAST_HASH_128;
      } else {
         int var1 = (var0 + 127) / 128;
         HashFunction[] var2 = new HashFunction[var1];
         var2[0] = Murmur3_128HashFunction.GOOD_FAST_HASH_128;
         int var3 = GOOD_FAST_HASH_SEED;

         for(var0 = 1; var0 < var1; ++var0) {
            var3 += 1500450271;
            var2[var0] = murmur3_128(var3);
         }

         return new Hashing.ConcatenatedHashFunction(var2);
      }
   }

   public static HashFunction hmacMd5(Key var0) {
      return new MacHashFunction("HmacMD5", var0, hmacToString("hmacMd5", var0));
   }

   public static HashFunction hmacMd5(byte[] var0) {
      return hmacMd5((Key)(new SecretKeySpec((byte[])Preconditions.checkNotNull(var0), "HmacMD5")));
   }

   public static HashFunction hmacSha1(Key var0) {
      return new MacHashFunction("HmacSHA1", var0, hmacToString("hmacSha1", var0));
   }

   public static HashFunction hmacSha1(byte[] var0) {
      return hmacSha1((Key)(new SecretKeySpec((byte[])Preconditions.checkNotNull(var0), "HmacSHA1")));
   }

   public static HashFunction hmacSha256(Key var0) {
      return new MacHashFunction("HmacSHA256", var0, hmacToString("hmacSha256", var0));
   }

   public static HashFunction hmacSha256(byte[] var0) {
      return hmacSha256((Key)(new SecretKeySpec((byte[])Preconditions.checkNotNull(var0), "HmacSHA256")));
   }

   public static HashFunction hmacSha512(Key var0) {
      return new MacHashFunction("HmacSHA512", var0, hmacToString("hmacSha512", var0));
   }

   public static HashFunction hmacSha512(byte[] var0) {
      return hmacSha512((Key)(new SecretKeySpec((byte[])Preconditions.checkNotNull(var0), "HmacSHA512")));
   }

   private static String hmacToString(String var0, Key var1) {
      return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", var0, var1.getAlgorithm(), var1.getFormat());
   }

   @Deprecated
   public static HashFunction md5() {
      return Hashing.Md5Holder.MD5;
   }

   public static HashFunction murmur3_128() {
      return Murmur3_128HashFunction.MURMUR3_128;
   }

   public static HashFunction murmur3_128(int var0) {
      return new Murmur3_128HashFunction(var0);
   }

   public static HashFunction murmur3_32() {
      return Murmur3_32HashFunction.MURMUR3_32;
   }

   public static HashFunction murmur3_32(int var0) {
      return new Murmur3_32HashFunction(var0);
   }

   @Deprecated
   public static HashFunction sha1() {
      return Hashing.Sha1Holder.SHA_1;
   }

   public static HashFunction sha256() {
      return Hashing.Sha256Holder.SHA_256;
   }

   public static HashFunction sha384() {
      return Hashing.Sha384Holder.SHA_384;
   }

   public static HashFunction sha512() {
      return Hashing.Sha512Holder.SHA_512;
   }

   public static HashFunction sipHash24() {
      return SipHashFunction.SIP_HASH_24;
   }

   public static HashFunction sipHash24(long var0, long var2) {
      return new SipHashFunction(2, 4, var0, var2);
   }

   @Immutable
   static enum ChecksumType implements ImmutableSupplier<Checksum> {
      ADLER_32,
      CRC_32("Hashing.crc32()") {
         public Checksum get() {
            return new CRC32();
         }
      };

      public final HashFunction hashFunction;

      static {
         Hashing.ChecksumType var0 = new Hashing.ChecksumType("ADLER_32", 1, "Hashing.adler32()") {
            public Checksum get() {
               return new Adler32();
            }
         };
         ADLER_32 = var0;
      }

      private ChecksumType(String var3) {
         this.hashFunction = new ChecksumHashFunction(this, 32, var3);
      }

      // $FF: synthetic method
      ChecksumType(String var3, Object var4) {
         this(var3);
      }
   }

   private static final class ConcatenatedHashFunction extends AbstractCompositeHashFunction {
      private ConcatenatedHashFunction(HashFunction... var1) {
         super(var1);
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            HashFunction var4 = var1[var3];
            boolean var5;
            if (var4.bits() % 8 == 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            Preconditions.checkArgument(var5, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", (int)var4.bits(), var4);
         }

      }

      // $FF: synthetic method
      ConcatenatedHashFunction(HashFunction[] var1, Object var2) {
         this(var1);
      }

      public int bits() {
         HashFunction[] var1 = this.functions;
         int var2 = var1.length;
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < var2; ++var3) {
            var4 += var1[var3].bits();
         }

         return var4;
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 instanceof Hashing.ConcatenatedHashFunction) {
            Hashing.ConcatenatedHashFunction var2 = (Hashing.ConcatenatedHashFunction)var1;
            return Arrays.equals(this.functions, var2.functions);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Arrays.hashCode(this.functions);
      }

      HashCode makeHash(Hasher[] var1) {
         byte[] var2 = new byte[this.bits() / 8];
         int var3 = var1.length;
         int var4 = 0;

         for(int var5 = 0; var4 < var3; ++var4) {
            HashCode var6 = var1[var4].hash();
            var5 += var6.writeBytesTo(var2, var5, var6.bits() / 8);
         }

         return HashCode.fromBytesNoCopy(var2);
      }
   }

   private static final class LinearCongruentialGenerator {
      private long state;

      public LinearCongruentialGenerator(long var1) {
         this.state = var1;
      }

      public double nextDouble() {
         long var1 = this.state * 2862933555777941757L + 1L;
         this.state = var1;
         return (double)((int)(var1 >>> 33) + 1) / 2.147483648E9D;
      }
   }

   private static class Md5Holder {
      static final HashFunction MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");
   }

   private static class Sha1Holder {
      static final HashFunction SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");
   }

   private static class Sha256Holder {
      static final HashFunction SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");
   }

   private static class Sha384Holder {
      static final HashFunction SHA_384 = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");
   }

   private static class Sha512Holder {
      static final HashFunction SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");
   }
}
