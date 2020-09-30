package io.opencensus.contrib.http.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import io.opencensus.stats.Stats;
import io.opencensus.stats.View;
import io.opencensus.stats.ViewManager;

public final class HttpViews {
   static final ImmutableSet<View> HTTP_CLIENT_VIEWS_SET;
   static final ImmutableSet<View> HTTP_SERVER_VIEWS_SET;

   static {
      HTTP_SERVER_VIEWS_SET = ImmutableSet.of(HttpViewConstants.HTTP_SERVER_COMPLETED_COUNT_VIEW, HttpViewConstants.HTTP_SERVER_SENT_BYTES_VIEW, HttpViewConstants.HTTP_SERVER_RECEIVED_BYTES_VIEW, HttpViewConstants.HTTP_SERVER_LATENCY_VIEW);
      HTTP_CLIENT_VIEWS_SET = ImmutableSet.of(HttpViewConstants.HTTP_CLIENT_COMPLETED_COUNT_VIEW, HttpViewConstants.HTTP_CLIENT_RECEIVED_BYTES_VIEW, HttpViewConstants.HTTP_CLIENT_SENT_BYTES_VIEW, HttpViewConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW);
   }

   private HttpViews() {
   }

   public static final void registerAllClientViews() {
      registerAllClientViews(Stats.getViewManager());
   }

   static void registerAllClientViews(ViewManager var0) {
      UnmodifiableIterator var1 = HTTP_CLIENT_VIEWS_SET.iterator();

      while(var1.hasNext()) {
         var0.registerView((View)var1.next());
      }

   }

   public static final void registerAllServerViews() {
      registerAllServerViews(Stats.getViewManager());
   }

   static void registerAllServerViews(ViewManager var0) {
      UnmodifiableIterator var1 = HTTP_SERVER_VIEWS_SET.iterator();

      while(var1.hasNext()) {
         var0.registerView((View)var1.next());
      }

   }

   public static final void registerAllViews() {
      registerAllViews(Stats.getViewManager());
   }

   static void registerAllViews(ViewManager var0) {
      registerAllClientViews(var0);
      registerAllServerViews(var0);
   }
}
