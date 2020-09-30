package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Random;
import javax.annotation.Nullable;

public final class TraceId implements Comparable<TraceId> {
   private static final int BASE16_SIZE = 32;
   public static final TraceId INVALID = new TraceId(0L, 0L);
   private static final long INVALID_ID = 0L;
   public static final int SIZE = 16;
   private final long idHi;
   private final long idLo;

   private TraceId(long var1, long var3) {
      this.idHi = var1;
      this.idLo = var3;
   }

   public static TraceId fromBytes(byte[] var0) {
      Utils.checkNotNull(var0, "src");
      boolean var1;
      if (var0.length == 16) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkArgument(var1, "Invalid size: expected %s, got %s", 16, var0.length);
      return fromBytes(var0, 0);
   }

   public static TraceId fromBytes(byte[] var0, int var1) {
      Utils.checkNotNull(var0, "src");
      return new TraceId(BigendianEncoding.longFromByteArray(var0, var1), BigendianEncoding.longFromByteArray(var0, var1 + 8));
   }

   public static TraceId fromLowerBase16(CharSequence var0) {
      Utils.checkNotNull(var0, "src");
      boolean var1;
      if (var0.length() == 32) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkArgument(var1, "Invalid size: expected %s, got %s", 32, var0.length());
      return fromLowerBase16(var0, 0);
   }

   public static TraceId fromLowerBase16(CharSequence var0, int var1) {
      Utils.checkNotNull(var0, "src");
      return new TraceId(BigendianEncoding.longFromBase16String(var0, var1), BigendianEncoding.longFromBase16String(var0, var1 + 16));
   }

   public static TraceId generateRandomId(Random var0) {
      long var1;
      long var3;
      do {
         var1 = var0.nextLong();
         var3 = var0.nextLong();
      } while(var1 == 0L && var3 == 0L);

      return new TraceId(var1, var3);
   }

   public int compareTo(TraceId var1) {
      long var2 = this.idHi;
      long var4 = var1.idHi;
      byte var6 = -1;
      if (var2 == var4) {
         var2 = this.idLo;
         var4 = var1.idLo;
         if (var2 == var4) {
            return 0;
         } else {
            if (var2 >= var4) {
               var6 = 1;
            }

            return var6;
         }
      } else {
         if (var2 >= var4) {
            var6 = 1;
         }

         return var6;
      }
   }

   public void copyBytesTo(byte[] var1, int var2) {
      BigendianEncoding.longToByteArray(this.idHi, var1, var2);
      BigendianEncoding.longToByteArray(this.idLo, var1, var2 + 8);
   }

   public void copyLowerBase16To(char[] var1, int var2) {
      BigendianEncoding.longToBase16String(this.idHi, var1, var2);
      BigendianEncoding.longToBase16String(this.idLo, var1, var2 + 16);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof TraceId)) {
         return false;
      } else {
         TraceId var3 = (TraceId)var1;
         if (this.idHi != var3.idHi || this.idLo != var3.idLo) {
            var2 = false;
         }

         return var2;
      }
   }

   public byte[] getBytes() {
      byte[] var1 = new byte[16];
      BigendianEncoding.longToByteArray(this.idHi, var1, 0);
      BigendianEncoding.longToByteArray(this.idLo, var1, 8);
      return var1;
   }

   public long getLowerLong() {
      long var1 = this.idHi;
      long var3 = var1;
      if (var1 < 0L) {
         var3 = -var1;
      }

      return var3;
   }

   public int hashCode() {
      long var1 = this.idHi;
      int var3 = (int)(var1 ^ var1 >>> 32);
      var1 = this.idLo;
      return (var3 + 31) * 31 + (int)(var1 ^ var1 >>> 32);
   }

   public boolean isValid() {
      boolean var1;
      if (this.idHi == 0L && this.idLo == 0L) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public String toLowerBase16() {
      char[] var1 = new char[32];
      this.copyLowerBase16To(var1, 0);
      return new String(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TraceId{traceId=");
      var1.append(this.toLowerBase16());
      var1.append("}");
      return var1.toString();
   }
}
