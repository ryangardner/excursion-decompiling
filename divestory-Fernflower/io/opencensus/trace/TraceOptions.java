package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Arrays;
import javax.annotation.Nullable;

public final class TraceOptions {
   private static final int BASE16_SIZE = 2;
   public static final TraceOptions DEFAULT = fromByte((byte)0);
   private static final byte DEFAULT_OPTIONS = 0;
   private static final byte IS_SAMPLED = 1;
   public static final int SIZE = 1;
   private final byte options;

   private TraceOptions(byte var1) {
      this.options = (byte)var1;
   }

   public static TraceOptions.Builder builder() {
      return new TraceOptions.Builder((byte)0);
   }

   public static TraceOptions.Builder builder(TraceOptions var0) {
      return new TraceOptions.Builder(var0.options);
   }

   public static TraceOptions fromByte(byte var0) {
      return new TraceOptions(var0);
   }

   @Deprecated
   public static TraceOptions fromBytes(byte[] var0) {
      Utils.checkNotNull(var0, "buffer");
      boolean var1;
      if (var0.length == 1) {
         var1 = true;
      } else {
         var1 = false;
      }

      Utils.checkArgument(var1, "Invalid size: expected %s, got %s", 1, var0.length);
      return fromByte(var0[0]);
   }

   @Deprecated
   public static TraceOptions fromBytes(byte[] var0, int var1) {
      Utils.checkIndex(var1, var0.length);
      return fromByte(var0[var1]);
   }

   public static TraceOptions fromLowerBase16(CharSequence var0, int var1) {
      return new TraceOptions(BigendianEncoding.byteFromBase16String(var0, var1));
   }

   private boolean hasOption(int var1) {
      boolean var2;
      if ((var1 & this.options) != 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void copyBytesTo(byte[] var1, int var2) {
      Utils.checkIndex(var2, var1.length);
      var1[var2] = (byte)this.options;
   }

   public void copyLowerBase16To(char[] var1, int var2) {
      BigendianEncoding.byteToBase16String(this.options, var1, var2);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof TraceOptions)) {
         return false;
      } else {
         TraceOptions var3 = (TraceOptions)var1;
         if (this.options != var3.options) {
            var2 = false;
         }

         return var2;
      }
   }

   public byte getByte() {
      return this.options;
   }

   @Deprecated
   public byte[] getBytes() {
      return new byte[]{this.options};
   }

   byte getOptions() {
      return this.options;
   }

   public int hashCode() {
      return Arrays.hashCode(new byte[]{this.options});
   }

   public boolean isSampled() {
      return this.hasOption(1);
   }

   public String toLowerBase16() {
      char[] var1 = new char[2];
      this.copyLowerBase16To(var1, 0);
      return new String(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TraceOptions{sampled=");
      var1.append(this.isSampled());
      var1.append("}");
      return var1.toString();
   }

   public static final class Builder {
      private byte options;

      private Builder(byte var1) {
         this.options = (byte)var1;
      }

      // $FF: synthetic method
      Builder(byte var1, Object var2) {
         this(var1);
      }

      public TraceOptions build() {
         return TraceOptions.fromByte(this.options);
      }

      @Deprecated
      public TraceOptions.Builder setIsSampled() {
         return this.setIsSampled(true);
      }

      public TraceOptions.Builder setIsSampled(boolean var1) {
         if (var1) {
            this.options = (byte)((byte)(this.options | 1));
         } else {
            this.options = (byte)((byte)(this.options & -2));
         }

         return this;
      }
   }
}
