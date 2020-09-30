/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.connection.RouteSelector$resetNextProxy
 */
package okhttp3.internal.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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
import okhttp3.Dns;
import okhttp3.EventListener;
import okhttp3.HttpUrl;
import okhttp3.Route;
import okhttp3.internal.connection.RouteDatabase;
import okhttp3.internal.connection.RouteSelector;

@Metadata(bv={1, 0, 3}, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 !2\u00020\u0001:\u0002!\"B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0015\u001a\u00020\u0016H\u0086\u0002J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\t\u0010\u0018\u001a\u00020\u0019H\u0086\u0002J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0014H\u0002J\u001a\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lokhttp3/internal/connection/RouteSelector;", "", "address", "Lokhttp3/Address;", "routeDatabase", "Lokhttp3/internal/connection/RouteDatabase;", "call", "Lokhttp3/Call;", "eventListener", "Lokhttp3/EventListener;", "(Lokhttp3/Address;Lokhttp3/internal/connection/RouteDatabase;Lokhttp3/Call;Lokhttp3/EventListener;)V", "inetSocketAddresses", "", "Ljava/net/InetSocketAddress;", "nextProxyIndex", "", "postponedRoutes", "", "Lokhttp3/Route;", "proxies", "Ljava/net/Proxy;", "hasNext", "", "hasNextProxy", "next", "Lokhttp3/internal/connection/RouteSelector$Selection;", "nextProxy", "resetNextInetSocketAddress", "", "proxy", "resetNextProxy", "url", "Lokhttp3/HttpUrl;", "Companion", "Selection", "okhttp"}, k=1, mv={1, 1, 16})
public final class RouteSelector {
    public static final Companion Companion = new Companion(null);
    private final Address address;
    private final Call call;
    private final EventListener eventListener;
    private List<? extends InetSocketAddress> inetSocketAddresses;
    private int nextProxyIndex;
    private final List<Route> postponedRoutes;
    private List<? extends Proxy> proxies;
    private final RouteDatabase routeDatabase;

    public RouteSelector(Address address, RouteDatabase routeDatabase, Call call, EventListener eventListener) {
        Intrinsics.checkParameterIsNotNull(address, "address");
        Intrinsics.checkParameterIsNotNull(routeDatabase, "routeDatabase");
        Intrinsics.checkParameterIsNotNull(call, "call");
        Intrinsics.checkParameterIsNotNull(eventListener, "eventListener");
        this.address = address;
        this.routeDatabase = routeDatabase;
        this.call = call;
        this.eventListener = eventListener;
        this.proxies = CollectionsKt.emptyList();
        this.inetSocketAddresses = CollectionsKt.emptyList();
        this.postponedRoutes = new ArrayList();
        this.resetNextProxy(this.address.url(), this.address.proxy());
    }

    public static final /* synthetic */ Address access$getAddress$p(RouteSelector routeSelector) {
        return routeSelector.address;
    }

    private final boolean hasNextProxy() {
        if (this.nextProxyIndex >= this.proxies.size()) return false;
        return true;
    }

    private final Proxy nextProxy() throws IOException {
        if (this.hasNextProxy()) {
            Object object = this.proxies;
            int n = this.nextProxyIndex;
            this.nextProxyIndex = n + 1;
            object = object.get(n);
            this.resetNextInetSocketAddress((Proxy)object);
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No route to ");
        stringBuilder.append(this.address.url().host());
        stringBuilder.append("; exhausted proxy configurations: ");
        stringBuilder.append(this.proxies);
        throw (Throwable)new SocketException(stringBuilder.toString());
    }

    private final void resetNextInetSocketAddress(Proxy iterator2) throws IOException {
        int n;
        Object object;
        List list;
        this.inetSocketAddresses = list = (List)new ArrayList();
        if (((Proxy)((Object)iterator2)).type() != Proxy.Type.DIRECT && ((Proxy)((Object)iterator2)).type() != Proxy.Type.SOCKS) {
            object = ((Proxy)((Object)iterator2)).address();
            if (!(object instanceof InetSocketAddress)) {
                iterator2 = new StringBuilder();
                ((StringBuilder)((Object)iterator2)).append("Proxy.address() is not an InetSocketAddress: ");
                ((StringBuilder)((Object)iterator2)).append(object.getClass());
                throw (Throwable)new IllegalArgumentException(((StringBuilder)((Object)iterator2)).toString().toString());
            }
            Companion companion = Companion;
            InetSocketAddress inetSocketAddress = (InetSocketAddress)object;
            object = companion.getSocketHost(inetSocketAddress);
            n = inetSocketAddress.getPort();
        } else {
            object = this.address.url().host();
            n = this.address.url().port();
        }
        if (1 <= n && 65535 >= n) {
            if (((Proxy)((Object)iterator2)).type() == Proxy.Type.SOCKS) {
                ((Collection)list).add(InetSocketAddress.createUnresolved((String)object, n));
                return;
            }
            this.eventListener.dnsStart(this.call, (String)object);
            iterator2 = this.address.dns().lookup((String)object);
            if (iterator2.isEmpty()) {
                iterator2 = new StringBuilder();
                ((StringBuilder)((Object)iterator2)).append(this.address.dns());
                ((StringBuilder)((Object)iterator2)).append(" returned no addresses for ");
                ((StringBuilder)((Object)iterator2)).append((String)object);
                throw (Throwable)new UnknownHostException(((StringBuilder)((Object)iterator2)).toString());
            }
            this.eventListener.dnsEnd(this.call, (String)object, (List<InetAddress>)((Object)iterator2));
            iterator2 = iterator2.iterator();
            while (iterator2.hasNext()) {
                object = (InetAddress)iterator2.next();
                ((Collection)list).add(new InetSocketAddress((InetAddress)object, n));
            }
            return;
        }
        iterator2 = new StringBuilder();
        ((StringBuilder)((Object)iterator2)).append("No route to ");
        ((StringBuilder)((Object)iterator2)).append((String)object);
        ((StringBuilder)((Object)iterator2)).append(':');
        ((StringBuilder)((Object)iterator2)).append(n);
        ((StringBuilder)((Object)iterator2)).append("; port is out of range");
        throw (Throwable)new SocketException(((StringBuilder)((Object)iterator2)).toString());
    }

    private final void resetNextProxy(HttpUrl httpUrl, Proxy object) {
        object = new Function0<List<? extends Proxy>>(this, (Proxy)object, httpUrl){
            final /* synthetic */ Proxy $proxy;
            final /* synthetic */ HttpUrl $url;
            final /* synthetic */ RouteSelector this$0;
            {
                this.this$0 = routeSelector;
                this.$proxy = proxy;
                this.$url = httpUrl;
                super(0);
            }

            public final List<Proxy> invoke() {
                List<Proxy> list = this.$proxy;
                if (list != null) {
                    return CollectionsKt.listOf(list);
                }
                list = this.$url.uri();
                if (((java.net.URI)((Object)list)).getHost() == null) {
                    return okhttp3.internal.Util.immutableListOf(Proxy.NO_PROXY);
                }
                list = RouteSelector.access$getAddress$p(this.this$0).proxySelector().select((java.net.URI)((Object)list));
                Collection collection = list;
                boolean bl = collection == null || collection.isEmpty();
                if (!bl) return okhttp3.internal.Util.toImmutableList(list);
                return okhttp3.internal.Util.immutableListOf(Proxy.NO_PROXY);
            }
        };
        this.eventListener.proxySelectStart(this.call, httpUrl);
        this.proxies = object = object.invoke();
        this.nextProxyIndex = 0;
        this.eventListener.proxySelectEnd(this.call, httpUrl, (List<Proxy>)object);
    }

    public final boolean hasNext() {
        boolean bl;
        boolean bl2 = this.hasNextProxy();
        boolean bl3 = bl = true;
        if (bl2) return bl3;
        if (!(((Collection)this.postponedRoutes).isEmpty() ^ true)) return false;
        return bl;
    }

    public final Selection next() throws IOException {
        if (!this.hasNext()) throw (Throwable)new NoSuchElementException();
        List list = new ArrayList();
        while (this.hasNextProxy()) {
            Proxy proxy = this.nextProxy();
            for (InetSocketAddress inetSocketAddress : this.inetSocketAddresses) {
                Route object = new Route(this.address, proxy, inetSocketAddress);
                if (this.routeDatabase.shouldPostpone(object)) {
                    ((Collection)this.postponedRoutes).add(object);
                    continue;
                }
                ((Collection)list).add(object);
            }
            if (!(((Collection)list).isEmpty() ^ true)) continue;
        }
        if (!list.isEmpty()) return new Selection(list);
        CollectionsKt.addAll((Collection)list, (Iterable)this.postponedRoutes);
        this.postponedRoutes.clear();
        return new Selection(list);
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lokhttp3/internal/connection/RouteSelector$Companion;", "", "()V", "socketHost", "", "Ljava/net/InetSocketAddress;", "getSocketHost", "(Ljava/net/InetSocketAddress;)Ljava/lang/String;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getSocketHost(InetSocketAddress object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$socketHost");
            InetAddress inetAddress = ((InetSocketAddress)object).getAddress();
            if (inetAddress != null) {
                object = inetAddress.getHostAddress();
                Intrinsics.checkExpressionValueIsNotNull(object, "address.hostAddress");
                return object;
            }
            object = ((InetSocketAddress)object).getHostName();
            Intrinsics.checkExpressionValueIsNotNull(object, "hostName");
            return object;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\n\u001a\u00020\u000bH\u0086\u0002J\t\u0010\f\u001a\u00020\u0004H\u0086\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, d2={"Lokhttp3/internal/connection/RouteSelector$Selection;", "", "routes", "", "Lokhttp3/Route;", "(Ljava/util/List;)V", "nextRouteIndex", "", "getRoutes", "()Ljava/util/List;", "hasNext", "", "next", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Selection {
        private int nextRouteIndex;
        private final List<Route> routes;

        public Selection(List<Route> list) {
            Intrinsics.checkParameterIsNotNull(list, "routes");
            this.routes = list;
        }

        public final List<Route> getRoutes() {
            return this.routes;
        }

        public final boolean hasNext() {
            if (this.nextRouteIndex >= this.routes.size()) return false;
            return true;
        }

        public final Route next() {
            if (!this.hasNext()) throw (Throwable)new NoSuchElementException();
            List<Route> list = this.routes;
            int n = this.nextRouteIndex;
            this.nextRouteIndex = n + 1;
            return list.get(n);
        }
    }

}

