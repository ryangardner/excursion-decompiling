package com.google.api.client.http;

import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableList;
import io.opencensus.contrib.http.util.HttpPropagationUtil;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.propagation.TextFormat;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class OpenCensusUtils {
   public static final String SPAN_NAME_HTTP_REQUEST_EXECUTE;
   private static final AtomicLong idGenerator;
   private static volatile boolean isRecordEvent;
   private static final Logger logger = Logger.getLogger(OpenCensusUtils.class.getName());
   @Nullable
   static volatile TextFormat propagationTextFormat;
   @Nullable
   static volatile TextFormat.Setter propagationTextFormatSetter;
   private static final Tracer tracer;

   static {
      StringBuilder var0 = new StringBuilder();
      var0.append("Sent.");
      var0.append(HttpRequest.class.getName());
      var0.append(".execute");
      SPAN_NAME_HTTP_REQUEST_EXECUTE = var0.toString();
      tracer = Tracing.getTracer();
      idGenerator = new AtomicLong();
      isRecordEvent = true;
      propagationTextFormat = null;
      propagationTextFormatSetter = null;

      try {
         propagationTextFormat = HttpPropagationUtil.getCloudTraceFormat();
         TextFormat.Setter var3 = new TextFormat.Setter<HttpHeaders>() {
            public void put(HttpHeaders var1, String var2, String var3) {
               var1.set(var2, var3);
            }
         };
         propagationTextFormatSetter = var3;
      } catch (Exception var2) {
         logger.log(Level.WARNING, "Cannot initialize default OpenCensus HTTP propagation text format.", var2);
      }

      try {
         Tracing.getExportComponent().getSampledSpanStore().registerSpanNamesForCollection(ImmutableList.of(SPAN_NAME_HTTP_REQUEST_EXECUTE));
      } catch (Exception var1) {
         logger.log(Level.WARNING, "Cannot register default OpenCensus span names for collection.", var1);
      }

   }

   private OpenCensusUtils() {
   }

   public static EndSpanOptions getEndSpanOptions(@Nullable Integer var0) {
      EndSpanOptions.Builder var1 = EndSpanOptions.builder();
      if (var0 == null) {
         var1.setStatus(Status.UNKNOWN);
      } else if (!HttpStatusCodes.isSuccess(var0)) {
         int var2 = var0;
         if (var2 != 400) {
            if (var2 != 401) {
               if (var2 != 403) {
                  if (var2 != 404) {
                     if (var2 != 412) {
                        if (var2 != 500) {
                           var1.setStatus(Status.UNKNOWN);
                        } else {
                           var1.setStatus(Status.UNAVAILABLE);
                        }
                     } else {
                        var1.setStatus(Status.FAILED_PRECONDITION);
                     }
                  } else {
                     var1.setStatus(Status.NOT_FOUND);
                  }
               } else {
                  var1.setStatus(Status.PERMISSION_DENIED);
               }
            } else {
               var1.setStatus(Status.UNAUTHENTICATED);
            }
         } else {
            var1.setStatus(Status.INVALID_ARGUMENT);
         }
      } else {
         var1.setStatus(Status.OK);
      }

      return var1.build();
   }

   public static Tracer getTracer() {
      return tracer;
   }

   public static boolean isRecordEvent() {
      return isRecordEvent;
   }

   public static void propagateTracingContext(Span var0, HttpHeaders var1) {
      boolean var2 = true;
      boolean var3;
      if (var0 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "span should not be null.");
      if (var1 != null) {
         var3 = var2;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "headers should not be null.");
      if (propagationTextFormat != null && propagationTextFormatSetter != null && !var0.equals(BlankSpan.INSTANCE)) {
         propagationTextFormat.inject(var0.getContext(), var1, propagationTextFormatSetter);
      }

   }

   static void recordMessageEvent(Span var0, long var1, MessageEvent.Type var3) {
      boolean var4;
      if (var0 != null) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "span should not be null.");
      long var5 = var1;
      if (var1 < 0L) {
         var5 = 0L;
      }

      var0.addMessageEvent(MessageEvent.builder(var3, idGenerator.getAndIncrement()).setUncompressedMessageSize(var5).build());
   }

   public static void recordReceivedMessageEvent(Span var0, long var1) {
      recordMessageEvent(var0, var1, MessageEvent.Type.RECEIVED);
   }

   public static void recordSentMessageEvent(Span var0, long var1) {
      recordMessageEvent(var0, var1, MessageEvent.Type.SENT);
   }

   public static void setIsRecordEvent(boolean var0) {
      isRecordEvent = var0;
   }

   public static void setPropagationTextFormat(@Nullable TextFormat var0) {
      propagationTextFormat = var0;
   }

   public static void setPropagationTextFormatSetter(@Nullable TextFormat.Setter var0) {
      propagationTextFormatSetter = var0;
   }
}
