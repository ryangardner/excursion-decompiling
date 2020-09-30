package kotlin.random;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b'\u0018\u0000 \u00182\u00020\u0001:\u0002\u0017\u0018B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H&J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016J$\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0004H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\u0018\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0016H\u0016¨\u0006\u0019"},
   d2 = {"Lkotlin/random/Random;", "", "()V", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "Companion", "Default", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public abstract class Random {
   public static final Random.Companion Companion;
   public static final Random.Default Default = new Random.Default((DefaultConstructorMarker)null);
   private static final Random defaultRandom;

   static {
      defaultRandom = PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
      Companion = Random.Companion.INSTANCE;
   }

   // $FF: synthetic method
   public static byte[] nextBytes$default(Random var0, byte[] var1, int var2, int var3, int var4, Object var5) {
      if (var5 == null) {
         if ((var4 & 2) != 0) {
            var2 = 0;
         }

         if ((var4 & 4) != 0) {
            var3 = var1.length;
         }

         return var0.nextBytes(var1, var2, var3);
      } else {
         throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nextBytes");
      }
   }

   public abstract int nextBits(int var1);

   public boolean nextBoolean() {
      boolean var1 = true;
      if (this.nextBits(1) == 0) {
         var1 = false;
      }

      return var1;
   }

   public byte[] nextBytes(int var1) {
      return this.nextBytes(new byte[var1]);
   }

   public byte[] nextBytes(byte[] var1) {
      Intrinsics.checkParameterIsNotNull(var1, "array");
      return this.nextBytes(var1, 0, var1.length);
   }

   public byte[] nextBytes(byte[] var1, int var2, int var3) {
      int var4;
      byte var5;
      boolean var6;
      boolean var10;
      label49: {
         Intrinsics.checkParameterIsNotNull(var1, "array");
         var4 = var1.length;
         var5 = 0;
         var6 = true;
         if (var2 >= 0 && var4 >= var2) {
            var4 = var1.length;
            if (var3 >= 0 && var4 >= var3) {
               var10 = true;
               break label49;
            }
         }

         var10 = false;
      }

      if (!var10) {
         StringBuilder var8 = new StringBuilder();
         var8.append("fromIndex (");
         var8.append(var2);
         var8.append(") or toIndex (");
         var8.append(var3);
         var8.append(") are out of range: 0..");
         var8.append(var1.length);
         var8.append('.');
         throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
      } else {
         if (var2 <= var3) {
            var10 = var6;
         } else {
            var10 = false;
         }

         if (!var10) {
            StringBuilder var9 = new StringBuilder();
            var9.append("fromIndex (");
            var9.append(var2);
            var9.append(") must be not greater than toIndex (");
            var9.append(var3);
            var9.append(").");
            throw (Throwable)(new IllegalArgumentException(var9.toString().toString()));
         } else {
            int var11 = (var3 - var2) / 4;

            for(var4 = 0; var4 < var11; ++var4) {
               int var7 = this.nextInt();
               var1[var2] = (byte)((byte)var7);
               var1[var2 + 1] = (byte)((byte)(var7 >>> 8));
               var1[var2 + 2] = (byte)((byte)(var7 >>> 16));
               var1[var2 + 3] = (byte)((byte)(var7 >>> 24));
               var2 += 4;
            }

            var4 = var3 - var2;
            var11 = this.nextBits(var4 * 8);

            for(var3 = var5; var3 < var4; ++var3) {
               var1[var2 + var3] = (byte)((byte)(var11 >>> var3 * 8));
            }

            return var1;
         }
      }
   }

   public double nextDouble() {
      return PlatformRandomKt.doubleFromParts(this.nextBits(26), this.nextBits(27));
   }

   public double nextDouble(double var1) {
      return this.nextDouble(0.0D, var1);
   }

   public double nextDouble(double var1, double var3) {
      double var5;
      label36: {
         RandomKt.checkRangeBounds(var1, var3);
         var5 = var3 - var1;
         if (Double.isInfinite(var5)) {
            boolean var7 = Double.isInfinite(var1);
            boolean var8 = true;
            boolean var9;
            if (!var7 && !Double.isNaN(var1)) {
               var9 = true;
            } else {
               var9 = false;
            }

            if (var9) {
               if (!Double.isInfinite(var3) && !Double.isNaN(var3)) {
                  var9 = var8;
               } else {
                  var9 = false;
               }

               if (var9) {
                  var5 = this.nextDouble();
                  double var10 = (double)2;
                  var5 *= var3 / var10 - var1 / var10;
                  var1 = var1 + var5 + var5;
                  break label36;
               }
            }
         }

         var1 += this.nextDouble() * var5;
      }

      var5 = var1;
      if (var1 >= var3) {
         var5 = Math.nextAfter(var3, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
      }

      return var5;
   }

   public float nextFloat() {
      return (float)this.nextBits(24) / (float)16777216;
   }

   public int nextInt() {
      return this.nextBits(32);
   }

   public int nextInt(int var1) {
      return this.nextInt(0, var1);
   }

   public int nextInt(int var1, int var2) {
      RandomKt.checkRangeBounds(var1, var2);
      int var3 = var2 - var1;
      if (var3 <= 0 && var3 != Integer.MIN_VALUE) {
         do {
            while(true) {
               var3 = this.nextInt();
               if (var1 > var3) {
                  continue;
               }
               break;
            }
         } while(var2 <= var3);

         return var3;
      } else {
         int var4;
         if ((-var3 & var3) == var3) {
            var2 = this.nextBits(RandomKt.fastLog2(var3));
         } else {
            do {
               var4 = this.nextInt() >>> 1;
               var2 = var4 % var3;
            } while(var4 - var2 + (var3 - 1) < 0);
         }

         return var1 + var2;
      }
   }

   public long nextLong() {
      return ((long)this.nextInt() << 32) + (long)this.nextInt();
   }

   public long nextLong(long var1) {
      return this.nextLong(0L, var1);
   }

   public long nextLong(long var1, long var3) {
      RandomKt.checkRangeBounds(var1, var3);
      long var5 = var3 - var1;
      if (var5 > 0L) {
         long var9;
         if ((-var5 & var5) == var5) {
            int var7 = (int)var5;
            int var8 = (int)(var5 >>> 32);
            if (var7 != 0) {
               var8 = this.nextBits(RandomKt.fastLog2(var7));
            } else {
               if (var8 != 1) {
                  var3 = ((long)this.nextBits(RandomKt.fastLog2(var8)) << 32) + (long)this.nextInt();
                  return var1 + var3;
               }

               var8 = this.nextInt();
            }

            var3 = (long)var8 & 4294967295L;
         } else {
            do {
               var9 = this.nextLong() >>> 1;
               var3 = var9 % var5;
            } while(var9 - var3 + (var5 - 1L) < 0L);
         }

         return var1 + var3;
      } else {
         do {
            while(true) {
               var5 = this.nextLong();
               if (var1 > var5) {
                  continue;
               }
               break;
            }
         } while(var3 <= var5);

         return var5;
      }
   }

   @Deprecated(
      level = DeprecationLevel.HIDDEN,
      message = "Use Default companion object instead"
   )
   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"},
      d2 = {"Lkotlin/random/Random$Companion;", "Lkotlin/random/Random;", "()V", "nextBits", "", "bitCount", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion extends Random {
      public static final Random.Companion INSTANCE = new Random.Companion();

      private Companion() {
      }

      public int nextBits(int var1) {
         return Random.Default.nextBits(var1);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\u0018\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\bH\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u001aH\u0016R\u0016\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002R\u000e\u0010\u0006\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
      d2 = {"Lkotlin/random/Random$Default;", "Lkotlin/random/Random;", "()V", "Companion", "Lkotlin/random/Random$Companion;", "Companion$annotations", "defaultRandom", "nextBits", "", "bitCount", "nextBoolean", "", "nextBytes", "", "array", "fromIndex", "toIndex", "size", "nextDouble", "", "until", "from", "nextFloat", "", "nextInt", "nextLong", "", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Default extends Random {
      private Default() {
      }

      // $FF: synthetic method
      public Default(DefaultConstructorMarker var1) {
         this();
      }

      // $FF: synthetic method
      @Deprecated(
         level = DeprecationLevel.HIDDEN,
         message = "Use Default companion object instead"
      )
      public static void Companion$annotations() {
      }

      public int nextBits(int var1) {
         return Random.defaultRandom.nextBits(var1);
      }

      public boolean nextBoolean() {
         return Random.defaultRandom.nextBoolean();
      }

      public byte[] nextBytes(int var1) {
         return Random.defaultRandom.nextBytes(var1);
      }

      public byte[] nextBytes(byte[] var1) {
         Intrinsics.checkParameterIsNotNull(var1, "array");
         return Random.defaultRandom.nextBytes(var1);
      }

      public byte[] nextBytes(byte[] var1, int var2, int var3) {
         Intrinsics.checkParameterIsNotNull(var1, "array");
         return Random.defaultRandom.nextBytes(var1, var2, var3);
      }

      public double nextDouble() {
         return Random.defaultRandom.nextDouble();
      }

      public double nextDouble(double var1) {
         return Random.defaultRandom.nextDouble(var1);
      }

      public double nextDouble(double var1, double var3) {
         return Random.defaultRandom.nextDouble(var1, var3);
      }

      public float nextFloat() {
         return Random.defaultRandom.nextFloat();
      }

      public int nextInt() {
         return Random.defaultRandom.nextInt();
      }

      public int nextInt(int var1) {
         return Random.defaultRandom.nextInt(var1);
      }

      public int nextInt(int var1, int var2) {
         return Random.defaultRandom.nextInt(var1, var2);
      }

      public long nextLong() {
         return Random.defaultRandom.nextLong();
      }

      public long nextLong(long var1) {
         return Random.defaultRandom.nextLong(var1);
      }

      public long nextLong(long var1, long var3) {
         return Random.defaultRandom.nextLong(var1, var3);
      }
   }
}
