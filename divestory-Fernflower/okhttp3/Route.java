package okhttp3;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u0012J\u0006\u0010\u0013\u001a\u00020\u000eJ\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0016R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\tR\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\nR\u0013\u0010\u0006\u001a\u00020\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000b¨\u0006\u0017"},
   d2 = {"Lokhttp3/Route;", "", "address", "Lokhttp3/Address;", "proxy", "Ljava/net/Proxy;", "socketAddress", "Ljava/net/InetSocketAddress;", "(Lokhttp3/Address;Ljava/net/Proxy;Ljava/net/InetSocketAddress;)V", "()Lokhttp3/Address;", "()Ljava/net/Proxy;", "()Ljava/net/InetSocketAddress;", "-deprecated_address", "equals", "", "other", "hashCode", "", "-deprecated_proxy", "requiresTunnel", "-deprecated_socketAddress", "toString", "", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Route {
   private final Address address;
   private final Proxy proxy;
   private final InetSocketAddress socketAddress;

   public Route(Address var1, Proxy var2, InetSocketAddress var3) {
      Intrinsics.checkParameterIsNotNull(var1, "address");
      Intrinsics.checkParameterIsNotNull(var2, "proxy");
      Intrinsics.checkParameterIsNotNull(var3, "socketAddress");
      super();
      this.address = var1;
      this.proxy = var2;
      this.socketAddress = var3;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "address",
   imports = {}
)
   )
   public final Address _deprecated_address/* $FF was: -deprecated_address*/() {
      return this.address;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "proxy",
   imports = {}
)
   )
   public final Proxy _deprecated_proxy/* $FF was: -deprecated_proxy*/() {
      return this.proxy;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "socketAddress",
   imports = {}
)
   )
   public final InetSocketAddress _deprecated_socketAddress/* $FF was: -deprecated_socketAddress*/() {
      return this.socketAddress;
   }

   public final Address address() {
      return this.address;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Route) {
         Route var3 = (Route)var1;
         if (Intrinsics.areEqual((Object)var3.address, (Object)this.address) && Intrinsics.areEqual((Object)var3.proxy, (Object)this.proxy) && Intrinsics.areEqual((Object)var3.socketAddress, (Object)this.socketAddress)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public int hashCode() {
      return ((527 + this.address.hashCode()) * 31 + this.proxy.hashCode()) * 31 + this.socketAddress.hashCode();
   }

   public final Proxy proxy() {
      return this.proxy;
   }

   public final boolean requiresTunnel() {
      boolean var1;
      if (this.address.sslSocketFactory() != null && this.proxy.type() == Type.HTTP) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final InetSocketAddress socketAddress() {
      return this.socketAddress;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Route{");
      var1.append(this.socketAddress);
      var1.append('}');
      return var1.toString();
   }
}
