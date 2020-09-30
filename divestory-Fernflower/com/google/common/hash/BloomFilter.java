package com.google.common.hash;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class BloomFilter<T> implements Predicate<T>, Serializable {
   private final BloomFilterStrategies.LockFreeBitArray bits;
   private final Funnel<? super T> funnel;
   private final int numHashFunctions;
   private final BloomFilter.Strategy strategy;

   private BloomFilter(BloomFilterStrategies.LockFreeBitArray var1, int var2, Funnel<? super T> var3, BloomFilter.Strategy var4) {
      boolean var5 = true;
      boolean var6;
      if (var2 > 0) {
         var6 = true;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "numHashFunctions (%s) must be > 0", var2);
      if (var2 <= 255) {
         var6 = var5;
      } else {
         var6 = false;
      }

      Preconditions.checkArgument(var6, "numHashFunctions (%s) must be <= 255", var2);
      this.bits = (BloomFilterStrategies.LockFreeBitArray)Preconditions.checkNotNull(var1);
      this.numHashFunctions = var2;
      this.funnel = (Funnel)Preconditions.checkNotNull(var3);
      this.strategy = (BloomFilter.Strategy)Preconditions.checkNotNull(var4);
   }

   // $FF: synthetic method
   BloomFilter(BloomFilterStrategies.LockFreeBitArray var1, int var2, Funnel var3, BloomFilter.Strategy var4, Object var5) {
      this(var1, var2, var3, var4);
   }

   public static <T> BloomFilter<T> create(Funnel<? super T> var0, int var1) {
      return create(var0, (long)var1);
   }

   public static <T> BloomFilter<T> create(Funnel<? super T> var0, int var1, double var2) {
      return create(var0, (long)var1, var2);
   }

   public static <T> BloomFilter<T> create(Funnel<? super T> var0, long var1) {
      return create(var0, var1, 0.03D);
   }

   public static <T> BloomFilter<T> create(Funnel<? super T> var0, long var1, double var3) {
      return create(var0, var1, var3, BloomFilterStrategies.MURMUR128_MITZ_64);
   }

   static <T> BloomFilter<T> create(Funnel<? super T> var0, long var1, double var3, BloomFilter.Strategy var5) {
      Preconditions.checkNotNull(var0);
      boolean var6 = true;
      long var15;
      int var7 = (var15 = var1 - 0L) == 0L ? 0 : (var15 < 0L ? -1 : 1);
      boolean var8;
      if (var7 >= 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "Expected insertions (%s) must be >= 0", var1);
      if (var3 > 0.0D) {
         var8 = true;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "False positive probability (%s) must be > 0.0", (Object)var3);
      if (var3 < 1.0D) {
         var8 = var6;
      } else {
         var8 = false;
      }

      Preconditions.checkArgument(var8, "False positive probability (%s) must be < 1.0", (Object)var3);
      Preconditions.checkNotNull(var5);
      if (var7 == 0) {
         var1 = 1L;
      }

      long var9 = optimalNumOfBits(var1, var3);
      var7 = optimalNumOfHashFunctions(var1, var9);

      try {
         BloomFilterStrategies.LockFreeBitArray var11 = new BloomFilterStrategies.LockFreeBitArray(var9);
         BloomFilter var13 = new BloomFilter(var11, var7, var0, var5);
         return var13;
      } catch (IllegalArgumentException var12) {
         StringBuilder var14 = new StringBuilder();
         var14.append("Could not create BloomFilter of ");
         var14.append(var9);
         var14.append(" bits");
         throw new IllegalArgumentException(var14.toString(), var12);
      }
   }

   static long optimalNumOfBits(long var0, double var2) {
      double var4 = var2;
      if (var2 == 0.0D) {
         var4 = Double.MIN_VALUE;
      }

      return (long)((double)(-var0) * Math.log(var4) / (Math.log(2.0D) * Math.log(2.0D)));
   }

   static int optimalNumOfHashFunctions(long var0, long var2) {
      return Math.max(1, (int)Math.round((double)var2 / (double)var0 * Math.log(2.0D)));
   }

   public static <T> BloomFilter<T> readFrom(InputStream var0, Funnel<? super T> var1) throws IOException {
      Preconditions.checkNotNull(var0, "InputStream");
      Preconditions.checkNotNull(var1, "Funnel");
      byte var2 = -1;

      byte var4;
      int var5;
      int var7;
      RuntimeException var14;
      label63: {
         DataInputStream var3;
         try {
            var3 = new DataInputStream(var0);
            var4 = var3.readByte();
         } catch (RuntimeException var13) {
            var14 = var13;
            var7 = -1;
            var5 = -1;
            var4 = var2;
            break label63;
         }

         int var17;
         label55: {
            label54: {
               try {
                  var5 = UnsignedBytes.toInt(var3.readByte());
               } catch (RuntimeException var12) {
                  var14 = var12;
                  var5 = -1;
                  break label54;
               }

               try {
                  var17 = var3.readInt();
                  break label55;
               } catch (RuntimeException var11) {
                  var14 = var11;
               }
            }

            var7 = -1;
            break label63;
         }

         RuntimeException var10000;
         label64: {
            BloomFilterStrategies var6;
            long[] var16;
            boolean var10001;
            try {
               var6 = BloomFilterStrategies.values()[var4];
               var16 = new long[var17];
            } catch (RuntimeException var10) {
               var10000 = var10;
               var10001 = false;
               break label64;
            }

            for(var7 = 0; var7 < var17; ++var7) {
               try {
                  var16[var7] = var3.readLong();
               } catch (RuntimeException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label64;
               }
            }

            try {
               BloomFilterStrategies.LockFreeBitArray var19 = new BloomFilterStrategies.LockFreeBitArray(var16);
               BloomFilter var18 = new BloomFilter(var19, var5, var1, var6);
               return var18;
            } catch (RuntimeException var8) {
               var10000 = var8;
               var10001 = false;
            }
         }

         var14 = var10000;
         var7 = var17;
      }

      StringBuilder var15 = new StringBuilder();
      var15.append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
      var15.append(var4);
      var15.append(" numHashFunctions: ");
      var15.append(var5);
      var15.append(" dataLength: ");
      var15.append(var7);
      throw new IOException(var15.toString(), var14);
   }

   private Object writeReplace() {
      return new BloomFilter.SerialForm(this);
   }

   @Deprecated
   public boolean apply(T var1) {
      return this.mightContain(var1);
   }

   public long approximateElementCount() {
      long var1 = this.bits.bitSize();
      double var3 = (double)this.bits.bitCount();
      double var5 = (double)var1;
      return DoubleMath.roundToLong(-Math.log1p(-(var3 / var5)) * var5 / (double)this.numHashFunctions, RoundingMode.HALF_UP);
   }

   long bitSize() {
      return this.bits.bitSize();
   }

   public BloomFilter<T> copy() {
      return new BloomFilter(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
   }

   public boolean equals(@NullableDecl Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BloomFilter)) {
         return false;
      } else {
         BloomFilter var3 = (BloomFilter)var1;
         if (this.numHashFunctions != var3.numHashFunctions || !this.funnel.equals(var3.funnel) || !this.bits.equals(var3.bits) || !this.strategy.equals(var3.strategy)) {
            var2 = false;
         }

         return var2;
      }
   }

   public double expectedFpp() {
      return Math.pow((double)this.bits.bitCount() / (double)this.bitSize(), (double)this.numHashFunctions);
   }

   public int hashCode() {
      return Objects.hashCode(this.numHashFunctions, this.funnel, this.strategy, this.bits);
   }

   public boolean isCompatible(BloomFilter<T> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if (this != var1 && this.numHashFunctions == var1.numHashFunctions && this.bitSize() == var1.bitSize() && this.strategy.equals(var1.strategy) && this.funnel.equals(var1.funnel)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean mightContain(T var1) {
      return this.strategy.mightContain(var1, this.funnel, this.numHashFunctions, this.bits);
   }

   public boolean put(T var1) {
      return this.strategy.put(var1, this.funnel, this.numHashFunctions, this.bits);
   }

   public void putAll(BloomFilter<T> var1) {
      Preconditions.checkNotNull(var1);
      boolean var2;
      if (this != var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "Cannot combine a BloomFilter with itself.");
      if (this.numHashFunctions == var1.numHashFunctions) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, var1.numHashFunctions);
      if (this.bitSize() == var1.bitSize()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), var1.bitSize());
      Preconditions.checkArgument(this.strategy.equals(var1.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, var1.strategy);
      Preconditions.checkArgument(this.funnel.equals(var1.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, var1.funnel);
      this.bits.putAll(var1.bits);
   }

   public void writeTo(OutputStream var1) throws IOException {
      DataOutputStream var3 = new DataOutputStream(var1);
      var3.writeByte(SignedBytes.checkedCast((long)this.strategy.ordinal()));
      var3.writeByte(UnsignedBytes.checkedCast((long)this.numHashFunctions));
      var3.writeInt(this.bits.data.length());

      for(int var2 = 0; var2 < this.bits.data.length(); ++var2) {
         var3.writeLong(this.bits.data.get(var2));
      }

   }

   private static class SerialForm<T> implements Serializable {
      private static final long serialVersionUID = 1L;
      final long[] data;
      final Funnel<? super T> funnel;
      final int numHashFunctions;
      final BloomFilter.Strategy strategy;

      SerialForm(BloomFilter<T> var1) {
         this.data = BloomFilterStrategies.LockFreeBitArray.toPlainArray(var1.bits.data);
         this.numHashFunctions = var1.numHashFunctions;
         this.funnel = var1.funnel;
         this.strategy = var1.strategy;
      }

      Object readResolve() {
         return new BloomFilter(new BloomFilterStrategies.LockFreeBitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
      }
   }

   interface Strategy extends Serializable {
      <T> boolean mightContain(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4);

      int ordinal();

      <T> boolean put(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4);
   }
}
