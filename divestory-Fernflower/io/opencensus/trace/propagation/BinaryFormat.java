package io.opencensus.trace.propagation;

import io.opencensus.internal.Utils;
import io.opencensus.trace.SpanContext;
import java.text.ParseException;

public abstract class BinaryFormat {
   static final BinaryFormat.NoopBinaryFormat NOOP_BINARY_FORMAT = new BinaryFormat.NoopBinaryFormat();

   static BinaryFormat getNoopBinaryFormat() {
      return NOOP_BINARY_FORMAT;
   }

   @Deprecated
   public SpanContext fromBinaryValue(byte[] var1) throws ParseException {
      try {
         SpanContext var3 = this.fromByteArray(var1);
         return var3;
      } catch (SpanContextParseException var2) {
         throw new ParseException(var2.toString(), 0);
      }
   }

   public SpanContext fromByteArray(byte[] var1) throws SpanContextParseException {
      try {
         SpanContext var3 = this.fromBinaryValue(var1);
         return var3;
      } catch (ParseException var2) {
         throw new SpanContextParseException("Error while parsing.", var2);
      }
   }

   @Deprecated
   public byte[] toBinaryValue(SpanContext var1) {
      return this.toByteArray(var1);
   }

   public byte[] toByteArray(SpanContext var1) {
      return this.toBinaryValue(var1);
   }

   private static final class NoopBinaryFormat extends BinaryFormat {
      private NoopBinaryFormat() {
      }

      // $FF: synthetic method
      NoopBinaryFormat(Object var1) {
         this();
      }

      public SpanContext fromByteArray(byte[] var1) {
         Utils.checkNotNull(var1, "bytes");
         return SpanContext.INVALID;
      }

      public byte[] toByteArray(SpanContext var1) {
         Utils.checkNotNull(var1, "spanContext");
         return new byte[0];
      }
   }
}
