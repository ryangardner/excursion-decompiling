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
import io.opencensus.trace.Link;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.SpanContextParseException;
import io.opencensus.trace.propagation.TextFormat;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public class HttpServerHandler<Q, P, C> extends AbstractHttpHandler<Q, P> {
   private final TextFormat.Getter<C> getter;
   private final Boolean publicEndpoint;
   private final StatsRecorder statsRecorder;
   private final Tagger tagger;
   private final TextFormat textFormat;
   private final Tracer tracer;

   public HttpServerHandler(Tracer var1, HttpExtractor<Q, P> var2, TextFormat var3, TextFormat.Getter<C> var4, Boolean var5) {
      super(var2);
      Preconditions.checkNotNull(var1, "tracer");
      Preconditions.checkNotNull(var3, "textFormat");
      Preconditions.checkNotNull(var4, "getter");
      Preconditions.checkNotNull(var5, "publicEndpoint");
      this.tracer = var1;
      this.textFormat = var3;
      this.getter = var4;
      this.publicEndpoint = var5;
      this.statsRecorder = Stats.getStatsRecorder();
      this.tagger = Tags.getTagger();
   }

   private void recordStats(HttpRequestContext var1, Q var2, int var3) {
      double var4 = (double)TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - var1.requestStartTime);
      String var6 = this.extractor.getMethod(var2);
      String var7 = this.extractor.getRoute(var2);
      TagContextBuilder var8 = this.tagger.toBuilder(var1.tagContext);
      TagKey var9 = HttpMeasureConstants.HTTP_SERVER_METHOD;
      String var10 = var6;
      if (var6 == null) {
         var10 = "";
      }

      TagContextBuilder var12 = var8.put(var9, TagValue.create(var10), HttpRequestContext.METADATA_NO_PROPAGATION);
      TagKey var14 = HttpMeasureConstants.HTTP_SERVER_ROUTE;
      var10 = var7;
      if (var7 == null) {
         var10 = "";
      }

      var12 = var12.put(var14, TagValue.create(var10), HttpRequestContext.METADATA_NO_PROPAGATION);
      TagKey var13 = HttpMeasureConstants.HTTP_SERVER_STATUS;
      if (var3 == 0) {
         var10 = "error";
      } else {
         var10 = Integer.toString(var3);
      }

      TagContext var11 = var12.put(var13, TagValue.create(var10), HttpRequestContext.METADATA_NO_PROPAGATION).build();
      this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_SERVER_LATENCY, var4).put(HttpMeasureConstants.HTTP_SERVER_RECEIVED_BYTES, var1.receiveMessageSize.get()).put(HttpMeasureConstants.HTTP_SERVER_SENT_BYTES, var1.sentMessageSize.get()).record(var11);
   }

   public void handleEnd(HttpRequestContext var1, Q var2, @Nullable P var3, @Nullable Throwable var4) {
      Preconditions.checkNotNull(var1, "context");
      Preconditions.checkNotNull(var2, "request");
      int var5 = this.extractor.getStatusCode(var3);
      this.recordStats(var1, var2, var5);
      this.spanEnd(var1.span, var5, var4);
   }

   public HttpRequestContext handleStart(C var1, Q var2) {
      Preconditions.checkNotNull(var1, "carrier");
      Preconditions.checkNotNull(var2, "request");
      String var3 = this.getSpanName(var2, this.extractor);

      SpanContext var5;
      try {
         var5 = this.textFormat.extract(var1, this.getter);
      } catch (SpanContextParseException var4) {
         var5 = null;
      }

      SpanBuilder var6;
      if (var5 != null && !this.publicEndpoint) {
         var6 = this.tracer.spanBuilderWithRemoteParent(var3, var5);
      } else {
         var6 = this.tracer.spanBuilder(var3);
      }

      Span var7 = var6.setSpanKind(Span.Kind.SERVER).startSpan();
      if (this.publicEndpoint && var5 != null) {
         var7.addLink(Link.fromSpanContext(var5, Link.Type.PARENT_LINKED_SPAN));
      }

      if (var7.getOptions().contains(Span.Options.RECORD_EVENTS)) {
         this.addSpanRequestAttributes(var7, var2, this.extractor);
      }

      return this.getNewContext(var7, this.tagger.getCurrentTagContext());
   }
}
