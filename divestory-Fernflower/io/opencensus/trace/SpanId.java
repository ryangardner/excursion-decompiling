package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Random;
import javax.annotation.Nullable;

public final class SpanId implements Comparable<SpanId> {
   private static final int BASE16_SIZE = 16;
   public static final SpanId INVALID = new SpanId(0L);
   private static final long INVALID_ID = 0L;
   public static final int SIZE = 8;
   private final long id;

   private SpanId(long var1) {
      this.id = var1;
   }

   public static SpanId fromBytes(byte[] var0) {
      Utils.checkNotNull(var0, "src");
      boolean var1;
      if (var0.length == 8) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkArgument(var1, "Invalid size: expected %s, got %s", 8, var0.length);
      return fromBytes(var0, 0);
   }

   public static SpanId fromBytes(byte[] var0, int var1) {
      Utils.checkNotNull(var0, "src");
      return new SpanId(BigendianEncoding.longFromByteArray(var0, var1));
   }

   public static SpanId fromLowerBase16(CharSequence var0) {
      Utils.checkNotNull(var0, "src");
      boolean var1;
      if (var0.length() == 16) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkArgument(var1, "Invalid size: expected %s, got %s", 16, var0.length());
      return fromLowerBase16(var0, 0);
   }

   public static SpanId fromLowerBase16(CharSequence var0, int var1) {
      Utils.checkNotNull(var0, "src");
      return new SpanId(BigendianEncoding.longFromBase16String(var0, var1));
   }

   public static SpanId generateRandomId(Random var0) {
      long var1;
      do {
         var1 = var0.nextLong();
      } while(var1 == 0L);

      return new SpanId(var1);
   }

   public int compareTo(SpanId var1) {
      long var2 = this.id;
      long var4 = var1.id;
      byte var6;
      if (var2 < var4) {
         var6 = -1;
      } else if (var2 == var4) {
         var6 = 0;
      } else {
         var6 = 1;
      }

      return var6;
   }

   public void copyBytesTo(byte[] var1, int var2) {
      BigendianEncoding.longToByteArray(this.id, var1, var2);
   }

   public void copyLowerBase16To(char[] var1, int var2) {
      BigendianEncoding.longToBase16String(this.id, var1, var2);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanId)) {
         return false;
      } else {
         SpanId var3 = (SpanId)var1;
         if (this.id != var3.id) {
            var2 = false;
         }

         return var2;
      }
   }

   public byte[] getBytes() {
      byte[] var1 = new byte[8];
      BigendianEncoding.longToByteArray(this.id, var1, 0);
      return var1;
   }

   public int hashCode() {
      long var1 = this.id;
      return (int)(var1 ^ var1 >>> 32);
   }

   public boolean isValid() {
      boolean var1;
      if (this.id != 0L) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toLowerBase16() {
      char[] var1 = new char[16];
      this.copyLowerBase16To(var1, 0);
      return new String(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("SpanId{spanId=");
      var1.append(this.toLowerBase16());
      var1.append("}");
      return var1.toString();
   }
}
