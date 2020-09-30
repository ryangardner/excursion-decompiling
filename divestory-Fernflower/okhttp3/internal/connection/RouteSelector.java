package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Address;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 !2\u00020\u0001:\u0002!\"B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0016H\u0086\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\t\u0010\u0018\u001a\u00020\u0019H\u0086\u0002J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0014H\u0002J\u001a\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"},
   d2 = {"Lokhttp3/internal/connection/RouteSelector;", "", "address", "Lokhttp3/Address;", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/Address;Lokhttp3/internal/connection/RouteDatabase;Lokhttp3/Call;Lokhttp3/EventListener;)V", "inetSocketAddresses", "", "Ljava/net/InetSocketAddress;", "nextProxyIndex", "", "postponedRoutes", "", "Lokhttp3/Route;", "proxies", "Ljava/net/Proxy;", "hasNext", "", "hasNextProxy", "next", "Lokhttp3/internal/connection/RouteSelector$Selection;", "nextProxy", "resetNextInetSocketAddress", "", "proxy", "resetNextProxy", "url", "Lokhttp3/HttpUrl;", "Companion", "Selection", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class RouteSelector {
   public static final RouteSelector.Companion Companion = new RouteSelector.Companion((DefaultConstructorMarker)null);
   private final Address address;
   private final Call call;
   private final EventListener eventListener;
   private List<? extends InetSocketAddress> inetSocketAddresses;
   private int nextProxyIndex;
   private final List<Route> postponedRoutes;
   private List<? extends Proxy> proxies;
   private final RouteDatabase routeDatabase;

   public RouteSelector(Address var1, RouteDatabase var2, Call var3, EventListener var4) {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      Intrinsics.checkParameterIsNotNull(var2, "routeDatabase");
      Intrinsics.checkParameterIsNotNull(var3, "call");
      Intrinsics.checkParameterIsNotNull(var4, "eventListener");
      super();
      this.address = var1;
      this.routeDatabase = var2;
      this.call = var3;
      this.eventListener = var4;
      this.proxies = CollectionsKt.emptyList();
      this.inetSocketAddresses = CollectionsKt.emptyList();
      this.postponedRoutes = (List)(new ArrayList());
      this.resetNextProxy(this.address.url(), this.address.proxy());
   }

   private final boolean hasNextProxy() {
      boolean var1;
      if (this.nextProxyIndex < this.proxies.size()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private final Proxy nextProxy() throws IOException {
      if (this.hasNextProxy()) {
         List var3 = this.proxies;
         int var2 = this.nextProxyIndex++;
         Proxy var4 = (Proxy)var3.get(var2);
         this.resetNextInetSocketAddress(var4);
         return var4;
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("No route to ");
         var1.append(this.address.url().host());
         var1.append("; exhausted proxy configurations: ");
         var1.append(this.proxies);
         throw (Throwable)(new SocketException(var1.toString()));
      }
   }

   private final void resetNextInetSocketAddress(Proxy var1) throws IOException {
      List var2 = (List)(new ArrayList());
      this.inetSocketAddresses = var2;
      String var3;
      int var6;
      StringBuilder var7;
      if (var1.type() != Type.DIRECT && var1.type() != Type.SOCKS) {
         SocketAddress var10 = var1.address();
         if (!(var10 instanceof InetSocketAddress)) {
            var7 = new StringBuilder();
            var7.append("Proxy.address() is not an InetSocketAddress: ");
            var7.append(var10.getClass());
            throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
         }

         RouteSelector.Companion var4 = Companion;
         InetSocketAddress var5 = (InetSocketAddress)var10;
         var3 = var4.getSocketHost(var5);
         var6 = var5.getPort();
      } else {
         var3 = this.address.url().host();
         var6 = this.address.url().port();
      }

      if (1 <= var6 && 65535 >= var6) {
         if (var1.type() == Type.SOCKS) {
            ((Collection)var2).add(InetSocketAddress.createUnresolved(var3, var6));
         } else {
            this.eventListener.dnsStart(this.call, var3);
            List var8 = this.address.dns().lookup(var3);
            if (var8.isEmpty()) {
               var7 = new StringBuilder();
               var7.append(this.address.dns());
               var7.append(" returned no addresses for ");
               var7.append(var3);
               throw (Throwable)(new UnknownHostException(var7.toString()));
            }

            this.eventListener.dnsEnd(this.call, var3, var8);
            Iterator var9 = var8.iterator();

            while(var9.hasNext()) {
               InetAddress var11 = (InetAddress)var9.next();
               ((Collection)var2).add(new InetSocketAddress(var11, var6));
            }
         }

      } else {
         var7 = new StringBuilder();
         var7.append("No route to ");
         var7.append(var3);
         var7.append(':');
         var7.append(var6);
         var7.append("; port is out of range");
         throw (Throwable)(new SocketException(var7.toString()));
      }
   }

   private final void resetNextProxy(final HttpUrl var1, final Proxy var2) {
      Function0 var3 = new Function0<List<? extends Proxy>>() {
         public final List<Proxy> invoke() {
            Proxy var1x = var2;
            if (var1x != null) {
               return CollectionsKt.listOf(var1x);
            } else {
               URI var4 = var1.uri();
               if (var4.getHost() == null) {
                  return Util.immutableListOf(Proxy.NO_PROXY);
               } else {
                  List var5 = RouteSelector.this.address.proxySelector().select(var4);
                  Collection var2x = (Collection)var5;
                  boolean var3;
                  if (var2x != null && !var2x.isEmpty()) {
                     var3 = false;
                  } else {
                     var3 = true;
                  }

                  return var3 ? Util.immutableListOf(Proxy.NO_PROXY) : Util.toImmutableList(var5);
               }
            }
         }
      };
      this.eventListener.proxySelectStart(this.call, var1);
      List var4 = var3.invoke();
      this.proxies = var4;
      this.nextProxyIndex = 0;
      this.eventListener.proxySelectEnd(this.call, var1, var4);
   }

   public final boolean hasNext() {
      boolean var1 = this.hasNextProxy();
      boolean var2 = true;
      boolean var3 = var2;
      if (!var1) {
         if (((Collection)this.postponedRoutes).isEmpty() ^ true) {
            var3 = var2;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   public final RouteSelector.Selection next() throws IOException {
      if (!this.hasNext()) {
         throw (Throwable)(new NoSuchElementException());
      } else {
         List var1 = (List)(new ArrayList());

         while(this.hasNextProxy()) {
            Proxy var2 = this.nextProxy();
            Iterator var3 = this.inetSocketAddresses.iterator();

            while(var3.hasNext()) {
               InetSocketAddress var4 = (InetSocketAddress)var3.next();
               Route var5 = new Route(this.address, var2, var4);
               if (this.routeDatabase.shouldPostpone(var5)) {
                  ((Collection)this.postponedRoutes).add(var5);
               } else {
                  ((Collection)var1).add(var5);
               }
            }

            if (((Collection)var1).isEmpty() ^ true) {
               break;
            }
         }

         if (var1.isEmpty()) {
            CollectionsKt.addAll((Collection)var1, (Iterable)this.postponedRoutes);
            this.postponedRoutes.clear();
         }

         return new RouteSelector.Selection(var1);
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
      d2 = {"Lokhttp3/internal/connection/RouteSelector$Companion;", "", "()V", "socketHost", "", "Ljava/net/InetSocketAddress;", "getSocketHost", "(Ljava/net/InetSocketAddress;)Ljava/lang/String;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final String getSocketHost(InetSocketAddress var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$socketHost");
         InetAddress var2 = var1.getAddress();
         String var3;
         if (var2 != null) {
            var3 = var2.getHostAddress();
            Intrinsics.checkExpressionValueIsNotNull(var3, "address.hostAddress");
            return var3;
         } else {
            var3 = var1.getHostName();
            Intrinsics.checkExpressionValueIsNotNull(var3, "hostName");
            return var3;
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\n\u001a\u00020\u000bH\u0086\u0002J\t\u0010\f\u001a\u00020\u0004H\u0086\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\r"},
      d2 = {"Lokhttp3/internal/connection/RouteSelector$Selection;", "", "routes", "", "Lokhttp3/Route;", "(Ljava/util/List;)V", "nextRouteIndex", "", "getRoutes", "()Ljava/util/List;", "hasNext", "", "next", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Selection {
      private int nextRouteIndex;
      private final List<Route> routes;

      public Selection(List<Route> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "routes");
         super();
         this.routes = var1;
      }

      public final List<Route> getRoutes() {
         return this.routes;
      }

      public final boolean hasNext() {
         boolean var1;
         if (this.nextRouteIndex < this.routes.size()) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public final Route next() {
         if (this.hasNext()) {
            List var1 = this.routes;
            int var2 = this.nextRouteIndex++;
            return (Route)var1.get(var2);
         } else {
            throw (Throwable)(new NoSuchElementException());
         }
      }
   }
}
