package io.opencensus.contrib.http;

import javax.annotation.Nullable;

public abstract class HttpExtractor<Q, P> {
   @Nullable
   public abstract String getHost(Q var1);

   @Nullable
   public abstract String getMethod(Q var1);

   @Nullable
   public abstract String getPath(Q var1);

   @Nullable
   public abstract String getRoute(Q var1);

   public abstract int getStatusCode(@Nullable P var1);

   @Nullable
   public abstract String getUrl(Q var1);

   @Nullable
   public abstract String getUserAgent(Q var1);
}
