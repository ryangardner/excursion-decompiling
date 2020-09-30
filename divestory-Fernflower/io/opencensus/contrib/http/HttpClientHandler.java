package io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import io.opencensus.contrib.http.util.HttpMeasureConstants;
import io.opencensus.stats.Stats;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagContextBuilder;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.Tags;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.TextFormat;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class HttpClientHandler<Q, P, C> extends AbstractHttpHandler<Q, P> {
   private final TextFormat.Setter<C> setter;
   private final StatsRecorder statsRecorder;
   private final Tagger tagger;
   private final TextFormat textFormat;
   private final Tracer tracer;

   public HttpClientHandler(Tracer var1, HttpExtractor<Q, P> var2, TextFormat var3, TextFormat.Setter<C> var4) {
      super(var2);
      Preconditions.checkNotNull(var4, "setter");
      Preconditions.checkNotNull(var3, "textFormat");
      Preconditions.checkNotNull(var1, "tracer");
      this.setter = var4;
      this.textFormat = var3;
      this.tracer = var1;
      this.statsRecorder = Stats.getStatsRecorder();
      this.tagger = Tags.getTagger();
   }

   private void recordStats(HttpRequestContext var1, @Nullable Q var2, int var3) {
      double var4 = (double)TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - var1.requestStartTime);
      String var6 = "";
      String var7;
      if (var2 == null) {
         var7 = "";
      } else {
         var7 = this.extractor.getMethod(var2);
      }

      String var13;
      if (var2 == null) {
         var13 = "null_request";
      } else {
         var13 = this.extractor.getHost(var2);
      }

      TagContextBuilder var8 = this.tagger.toBuilder(var1.tagContext);
      TagKey var9 = HttpMeasureConstants.HTTP_CLIENT_HOST;
      String var10 = var13;
      if (var13 == null) {
         var10 = "null_host";
      }

      TagContextBuilder var11 = var8.put(var9, TagValue.create(var10), HttpRequestContext.METADATA_NO_PROPAGATION);
      TagKey var16 = HttpMeasureConstants.HTTP_CLIENT_METHOD;
      if (var7 == null) {
         var13 = var6;
      } else {
         var13 = var7;
      }

      TagContextBuilder var15 = var11.put(var16, TagValue.create(var13), HttpRequestContext.METADATA_NO_PROPAGATION);
      TagKey var12 = HttpMeasureConstants.HTTP_CLIENT_STATUS;
      if (var3 == 0) {
         var13 = "error";
      } else {
         var13 = Integer.toString(var3);
      }

      TagContext var14 = var15.put(var12, TagValue.create(var13), HttpRequestContext.METADATA_NO_PROPAGATION).build();
      this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, var4).put(HttpMeasureConstants.HTTP_CLIENT_SENT_BYTES, var1.sentMessageSize.get()).put(HttpMeasureConstants.HTTP_CLIENT_RECEIVED_BYTES, var1.receiveMessageSize.get()).record(var14);
   }

   public void handleEnd(HttpRequestContext var1, @Nullable Q var2, @Nullable P var3, @Nullable Throwable var4) {
      Preconditions.checkNotNull(var1, "context");
      int var5 = this.extractor.getStatusCode(var3);
      this.recordStats(var1, var2, var5);
      this.spanEnd(var1.span, var5, var4);
   }

   public HttpRequestContext handleStart(@Nullable Span var1, C var2, Q var3) {
      Preconditions.checkNotNull(var2, "carrier");
      Preconditions.checkNotNull(var3, "request");
      Span var4 = var1;
      if (var1 == null) {
         var4 = this.tracer.getCurrentSpan();
      }

      String var5 = this.getSpanName(var3, this.extractor);
      var1 = this.tracer.spanBuilderWithExplicitParent(var5, var4).setSpanKind(Span.Kind.CLIENT).startSpan();
      if (var1.getOptions().contains(Span.Options.RECORD_EVENTS)) {
         this.addSpanRequestAttributes(var1, var3, this.extractor);
      }

      SpanContext var6 = var1.getContext();
      if (!var6.equals(SpanContext.INVALID)) {
         this.textFormat.inject(var6, var2, this.setter);
      }

      return this.getNewContext(var1, this.tagger.getCurrentTagContext());
   }
}
