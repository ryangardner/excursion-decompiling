package io.opencensus.contrib.http.util;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInts;
import com.google.common.primitives.UnsignedLongs;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.Tracestate;
import io.opencensus.trace.propagation.SpanContextParseException;
import io.opencensus.trace.propagation.TextFormat;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

final class CloudTraceFormat extends TextFormat {
   static final int CLOUD_TRACE_IS_SAMPLED = 1;
   static final List<String> FIELDS = Collections.singletonList("X-Cloud-Trace-Context");
   static final String HEADER_NAME = "X-Cloud-Trace-Context";
   static final int MIN_HEADER_SIZE = 34;
   static final String NOT_SAMPLED = "0";
   static final TraceOptions OPTIONS_NOT_SAMPLED;
   static final TraceOptions OPTIONS_SAMPLED = TraceOptions.builder().setIsSampled(true).build();
   static final String SAMPLED = "1";
   static final char SPAN_ID_DELIMITER = '/';
   static final int SPAN_ID_START_POS = 33;
   private static final Tracestate TRACESTATE_DEFAULT;
   static final int TRACE_ID_SIZE = 32;
   static final String TRACE_OPTION_DELIMITER = ";o=";
   static final int TRACE_OPTION_DELIMITER_SIZE;

   static {
      OPTIONS_NOT_SAMPLED = TraceOptions.DEFAULT;
      TRACE_OPTION_DELIMITER_SIZE = 3;
      TRACESTATE_DEFAULT = Tracestate.builder().build();
   }

   private static SpanId longToSpanId(long var0) {
      ByteBuffer var2 = ByteBuffer.allocate(8);
      var2.putLong(var0);
      return SpanId.fromBytes(var2.array());
   }

   private static long spanIdToLong(SpanId var0) {
      ByteBuffer var1 = ByteBuffer.allocate(8);
      var1.put(var0.getBytes());
      return var1.getLong(0);
   }

   public <C> SpanContext extract(C var1, TextFormat.Getter<C> var2) throws SpanContextParseException {
      Preconditions.checkNotNull(var1, "carrier");
      Preconditions.checkNotNull(var2, "getter");

      IllegalArgumentException var10000;
      label84: {
         String var3;
         boolean var10001;
         try {
            var3 = var2.get(var1, "X-Cloud-Trace-Context");
         } catch (IllegalArgumentException var16) {
            var10000 = var16;
            var10001 = false;
            break label84;
         }

         boolean var4;
         label87: {
            if (var3 != null) {
               label86: {
                  label73: {
                     try {
                        if (var3.length() < 34) {
                           break label86;
                        }

                        if (var3.charAt(32) == '/') {
                           break label73;
                        }
                     } catch (IllegalArgumentException var15) {
                        var10000 = var15;
                        var10001 = false;
                        break label84;
                     }

                     var4 = false;
                     break label87;
                  }

                  var4 = true;
                  break label87;
               }
            }

            try {
               SpanContextParseException var20 = new SpanContextParseException("Missing or too short header: X-Cloud-Trace-Context");
               throw var20;
            } catch (IllegalArgumentException var14) {
               var10000 = var14;
               var10001 = false;
               break label84;
            }
         }

         TraceId var5;
         int var6;
         try {
            Preconditions.checkArgument(var4, "Invalid TRACE_ID size");
            var5 = TraceId.fromLowerBase16(var3.subSequence(0, 32));
            var6 = var3.indexOf(";o=", 32);
         } catch (IllegalArgumentException var13) {
            var10000 = var13;
            var10001 = false;
            break label84;
         }

         int var7;
         if (var6 < 0) {
            try {
               var7 = var3.length();
            } catch (IllegalArgumentException var12) {
               var10000 = var12;
               var10001 = false;
               break label84;
            }
         } else {
            var7 = var6;
         }

         SpanId var8;
         TraceOptions var18;
         try {
            var8 = longToSpanId(UnsignedLongs.parseUnsignedLong(var3.subSequence(33, var7).toString(), 10));
            var18 = OPTIONS_NOT_SAMPLED;
         } catch (IllegalArgumentException var11) {
            var10000 = var11;
            var10001 = false;
            break label84;
         }

         TraceOptions var17 = var18;
         if (var6 > 0) {
            var17 = var18;

            try {
               if ((UnsignedInts.parseUnsignedInt(var3.substring(var6 + TRACE_OPTION_DELIMITER_SIZE), 10) & 1) != 0) {
                  var17 = OPTIONS_SAMPLED;
               }
            } catch (IllegalArgumentException var10) {
               var10000 = var10;
               var10001 = false;
               break label84;
            }
         }

         try {
            return SpanContext.create(var5, var8, var17, TRACESTATE_DEFAULT);
         } catch (IllegalArgumentException var9) {
            var10000 = var9;
            var10001 = false;
         }
      }

      IllegalArgumentException var19 = var10000;
      throw new SpanContextParseException("Invalid input", var19);
   }

   public List<String> fields() {
      return FIELDS;
   }

   public <C> void inject(SpanContext var1, C var2, TextFormat.Setter<C> var3) {
      Preconditions.checkNotNull(var1, "spanContext");
      Preconditions.checkNotNull(var3, "setter");
      Preconditions.checkNotNull(var2, "carrier");
      StringBuilder var4 = new StringBuilder();
      var4.append(var1.getTraceId().toLowerBase16());
      var4.append('/');
      var4.append(UnsignedLongs.toString(spanIdToLong(var1.getSpanId())));
      var4.append(";o=");
      String var5;
      if (var1.getTraceOptions().isSampled()) {
         var5 = "1";
      } else {
         var5 = "0";
      }

      var4.append(var5);
      var3.put(var2, "X-Cloud-Trace-Context", var4.toString());
   }
}
