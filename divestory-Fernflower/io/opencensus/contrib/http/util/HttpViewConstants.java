package io.opencensus.contrib.http.util;

import io.opencensus.stats.Aggregation;
import io.opencensus.stats.BucketBoundaries;
import io.opencensus.stats.View;
import java.util.Arrays;
import java.util.Collections;

public final class HttpViewConstants {
   static final Aggregation COUNT = Aggregation.Count.create();
   public static final View HTTP_CLIENT_COMPLETED_COUNT_VIEW;
   public static final View HTTP_CLIENT_RECEIVED_BYTES_VIEW;
   public static final View HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW;
   public static final View HTTP_CLIENT_SENT_BYTES_VIEW;
   public static final View HTTP_SERVER_COMPLETED_COUNT_VIEW;
   public static final View HTTP_SERVER_LATENCY_VIEW;
   public static final View HTTP_SERVER_RECEIVED_BYTES_VIEW;
   public static final View HTTP_SERVER_SENT_BYTES_VIEW;
   static final Aggregation LATENCY_DISTRIBUTION;
   static final Aggregation SIZE_DISTRIBUTION;

   static {
      Double var0 = 0.0D;
      SIZE_DISTRIBUTION = Aggregation.Distribution.create(BucketBoundaries.create(Collections.unmodifiableList(Arrays.asList(var0, 1024.0D, 2048.0D, 4096.0D, 16384.0D, 65536.0D, 262144.0D, 1048576.0D, 4194304.0D, 1.6777216E7D, 6.7108864E7D, 2.68435456E8D, 1.073741824E9D, 4.294967296E9D))));
      LATENCY_DISTRIBUTION = Aggregation.Distribution.create(BucketBoundaries.create(Collections.unmodifiableList(Arrays.asList(var0, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D, 8.0D, 10.0D, 13.0D, 16.0D, 20.0D, 25.0D, 30.0D, 40.0D, 50.0D, 65.0D, 80.0D, 100.0D, 130.0D, 160.0D, 200.0D, 250.0D, 300.0D, 400.0D, 500.0D, 650.0D, 800.0D, 1000.0D, 2000.0D, 5000.0D, 10000.0D, 20000.0D, 50000.0D, 100000.0D))));
      HTTP_CLIENT_COMPLETED_COUNT_VIEW = View.create(View.Name.create("opencensus.io/http/client/completed_count"), "Count of client-side HTTP requests completed", HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, COUNT, Arrays.asList(HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS));
      HTTP_CLIENT_SENT_BYTES_VIEW = View.create(View.Name.create("opencensus.io/http/client/sent_bytes"), "Size distribution of client-side HTTP request body", HttpMeasureConstants.HTTP_CLIENT_SENT_BYTES, SIZE_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS));
      HTTP_CLIENT_RECEIVED_BYTES_VIEW = View.create(View.Name.create("opencensus.io/http/client/received_bytes"), "Size distribution of client-side HTTP response body", HttpMeasureConstants.HTTP_CLIENT_RECEIVED_BYTES, SIZE_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS));
      HTTP_CLIENT_ROUNDTRIP_LATENCY_VIEW = View.create(View.Name.create("opencensus.io/http/client/roundtrip_latency"), "Roundtrip latency distribution of client-side HTTP requests", HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, LATENCY_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_CLIENT_METHOD, HttpMeasureConstants.HTTP_CLIENT_STATUS));
      HTTP_SERVER_COMPLETED_COUNT_VIEW = View.create(View.Name.create("opencensus.io/http/server/completed_count"), "Count of HTTP server-side requests serving completed", HttpMeasureConstants.HTTP_SERVER_LATENCY, COUNT, Arrays.asList(HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS));
      HTTP_SERVER_RECEIVED_BYTES_VIEW = View.create(View.Name.create("opencensus.io/http/server/received_bytes"), "Size distribution of server-side HTTP request body", HttpMeasureConstants.HTTP_SERVER_RECEIVED_BYTES, SIZE_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS));
      HTTP_SERVER_SENT_BYTES_VIEW = View.create(View.Name.create("opencensus.io/http/server/sent_bytes"), "Size distribution of server-side HTTP response body", HttpMeasureConstants.HTTP_SERVER_SENT_BYTES, SIZE_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS));
      HTTP_SERVER_LATENCY_VIEW = View.create(View.Name.create("opencensus.io/http/server/server_latency"), "Latency distribution of server-side HTTP requests serving", HttpMeasureConstants.HTTP_SERVER_LATENCY, LATENCY_DISTRIBUTION, Arrays.asList(HttpMeasureConstants.HTTP_SERVER_METHOD, HttpMeasureConstants.HTTP_SERVER_ROUTE, HttpMeasureConstants.HTTP_SERVER_STATUS));
   }

   private HttpViewConstants() {
   }
}
