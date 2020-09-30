package org.apache.http.impl.conn.tsccm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.apache.http.conn.routing.HttpRoute;

public class BasicPoolEntryRef extends WeakReference<BasicPoolEntry> {
   private final HttpRoute route;

   public BasicPoolEntryRef(BasicPoolEntry var1, ReferenceQueue<Object> var2) {
      super(var1, var2);
      if (var1 != null) {
         this.route = var1.getPlannedRoute();
      } else {
         throw new IllegalArgumentException("Pool entry must not be null.");
      }
   }

   public final HttpRoute getRoute() {
      return this.route;
   }
}
