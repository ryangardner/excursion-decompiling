package io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import io.opencensus.contrib.http.util.HttpTraceUtil;
import io.opencensus.tags.TagContext;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import javax.annotation.Nullable;

abstract class AbstractHttpHandler<Q, P> {
   final HttpExtractor<Q, P> extractor;

   AbstractHttpHandler(HttpExtractor<Q, P> var1) {
      Preconditions.checkNotNull(var1, "extractor");
      this.extractor = var1;
   }

   private static void putAttributeIfNotEmptyOrNull(Span var0, String var1, @Nullable String var2) {
      if (var2 != null && !var2.isEmpty()) {
         var0.putAttribute(var1, AttributeValue.stringAttributeValue(var2));
      }

   }

   static void recordMessageEvent(Span var0, long var1, MessageEvent.Type var3, long var4, long var6) {
      var0.addMessageEvent(MessageEvent.builder(var3, var1).setUncompressedMessageSize(var4).setCompressedMessageSize(var6).build());
   }

   final void addSpanRequestAttributes(Span var1, Q var2, HttpExtractor<Q, P> var3) {
      putAttributeIfNotEmptyOrNull(var1, "http.user_agent", var3.getUserAgent(var2));
      putAttributeIfNotEmptyOrNull(var1, "http.host", var3.getHost(var2));
      putAttributeIfNotEmptyOrNull(var1, "http.method", var3.getMethod(var2));
      putAttributeIfNotEmptyOrNull(var1, "http.path", var3.getPath(var2));
      putAttributeIfNotEmptyOrNull(var1, "http.route", var3.getRoute(var2));
      putAttributeIfNotEmptyOrNull(var1, "http.url", var3.getUrl(var2));
   }

   HttpRequestContext getNewContext(Span var1, TagContext var2) {
      return new HttpRequestContext(var1, var2);
   }

   public Span getSpanFromContext(HttpRequestContext var1) {
      Preconditions.checkNotNull(var1, "context");
      return var1.span;
   }

   final String getSpanName(Q var1, HttpExtractor<Q, P> var2) {
      String var4 = var2.getPath(var1);
      String var3 = var4;
      if (var4 == null) {
         var3 = "/";
      }

      var4 = var3;
      if (!var3.startsWith("/")) {
         StringBuilder var5 = new StringBuilder();
         var5.append("/");
         var5.append(var3);
         var4 = var5.toString();
      }

      return var4;
   }

   public final void handleMessageReceived(HttpRequestContext var1, long var2) {
      Preconditions.checkNotNull(var1, "context");
      var1.receiveMessageSize.addAndGet(var2);
      if (var1.span.getOptions().contains(Span.Options.RECORD_EVENTS)) {
         recordMessageEvent(var1.span, var1.receviedSeqId.addAndGet(1L), MessageEvent.Type.RECEIVED, var2, 0L);
      }

   }

   public final void handleMessageSent(HttpRequestContext var1, long var2) {
      Preconditions.checkNotNull(var1, "context");
      var1.sentMessageSize.addAndGet(var2);
      if (var1.span.getOptions().contains(Span.Options.RECORD_EVENTS)) {
         recordMessageEvent(var1.span, var1.sentSeqId.addAndGet(1L), MessageEvent.Type.SENT, var2, 0L);
      }

   }

   void spanEnd(Span var1, int var2, @Nullable Throwable var3) {
      if (var1.getOptions().contains(Span.Options.RECORD_EVENTS)) {
         var1.putAttribute("http.status_code", AttributeValue.longAttributeValue((long)var2));
         var1.setStatus(HttpTraceUtil.parseResponseStatus(var2, var3));
      }

      var1.end();
   }
}
