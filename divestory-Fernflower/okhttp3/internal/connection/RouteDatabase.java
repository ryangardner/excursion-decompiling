package okhttp3.internal.connection;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Route;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lokhttp3/internal/connection/RouteDatabase;", "", "()V", "failedRoutes", "", "Lokhttp3/Route;", "connected", "", "route", "failed", "failedRoute", "shouldPostpone", "", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RouteDatabase {
   private final Set<Route> failedRoutes = (Set)(new LinkedHashSet());

   public final void connected(Route var1) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "route");
         this.failedRoutes.remove(var1);
      } finally {
         ;
      }

   }

   public final void failed(Route var1) {
      synchronized(this){}

      try {
         Intrinsics.checkParameterIsNotNull(var1, "failedRoute");
         this.failedRoutes.add(var1);
      } finally {
         ;
      }

   }

   public final boolean shouldPostpone(Route var1) {
      synchronized(this){}

      boolean var2;
      try {
         Intrinsics.checkParameterIsNotNull(var1, "route");
         var2 = this.failedRoutes.contains(var1);
      } finally {
         ;
      }

      return var2;
   }
}
