package okhttp3.internal.authenticator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.Authenticator.RequestorType;
import java.net.Proxy.Type;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Address;
import okhttp3.Authenticator;
import okhttp3.Challenge;
import okhttp3.Credentials;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lokhttp3/internal/authenticator/JavaNetAuthenticator;", "Lokhttp3/Authenticator;", "defaultDns", "Lokhttp3/Dns;", "(Lokhttp3/Dns;)V", "authenticate", "Lokhttp3/Request;", "route", "Lokhttp3/Route;", "response", "Lokhttp3/Response;", "connectToInetAddress", "Ljava/net/InetAddress;", "Ljava/net/Proxy;", "url", "Lokhttp3/HttpUrl;", "dns", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class JavaNetAuthenticator implements Authenticator {
   private final Dns defaultDns;

   public JavaNetAuthenticator() {
      this((Dns)null, 1, (DefaultConstructorMarker)null);
   }

   public JavaNetAuthenticator(Dns var1) {
      Intrinsics.checkParameterIsNotNull(var1, "defaultDns");
      super();
      this.defaultDns = var1;
   }

   // $FF: synthetic method
   public JavaNetAuthenticator(Dns var1, int var2, DefaultConstructorMarker var3) {
      if ((var2 & 1) != 0) {
         var1 = Dns.SYSTEM;
      }

      this(var1);
   }

   private final InetAddress connectToInetAddress(Proxy var1, HttpUrl var2, Dns var3) throws IOException {
      Type var4 = var1.type();
      InetAddress var6;
      if (var4 != null && JavaNetAuthenticator$WhenMappings.$EnumSwitchMapping$0[var4.ordinal()] == 1) {
         var6 = (InetAddress)CollectionsKt.first(var3.lookup(var2.host()));
      } else {
         SocketAddress var5 = var1.address();
         if (var5 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
         }

         var6 = ((InetSocketAddress)var5).getAddress();
         Intrinsics.checkExpressionValueIsNotNull(var6, "(address() as InetSocketAddress).address");
      }

      return var6;
   }

   public Request authenticate(Route var1, Response var2) throws IOException {
      Intrinsics.checkParameterIsNotNull(var2, "response");
      List var3 = var2.challenges();
      Request var4 = var2.request();
      HttpUrl var5 = var4.url();
      boolean var6;
      if (var2.code() == 407) {
         var6 = true;
      } else {
         var6 = false;
      }

      Proxy var12;
      label56: {
         if (var1 != null) {
            var12 = var1.proxy();
            if (var12 != null) {
               break label56;
            }
         }

         var12 = Proxy.NO_PROXY;
      }

      Iterator var7 = var3.iterator();

      Challenge var8;
      PasswordAuthentication var16;
      do {
         do {
            if (!var7.hasNext()) {
               return null;
            }

            var8 = (Challenge)var7.next();
         } while(!StringsKt.equals("Basic", var8.scheme(), true));

         Dns var14;
         label48: {
            if (var1 != null) {
               Address var13 = var1.address();
               if (var13 != null) {
                  var14 = var13.dns();
                  if (var14 != null) {
                     break label48;
                  }
               }
            }

            var14 = this.defaultDns;
         }

         if (var6) {
            SocketAddress var9 = var12.address();
            if (var9 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
            }

            InetSocketAddress var18 = (InetSocketAddress)var9;
            String var10 = var18.getHostName();
            Intrinsics.checkExpressionValueIsNotNull(var12, "proxy");
            var16 = java.net.Authenticator.requestPasswordAuthentication(var10, this.connectToInetAddress(var12, var5, var14), var18.getPort(), var5.scheme(), var8.realm(), var8.scheme(), var5.url(), RequestorType.PROXY);
         } else {
            String var19 = var5.host();
            Intrinsics.checkExpressionValueIsNotNull(var12, "proxy");
            var16 = java.net.Authenticator.requestPasswordAuthentication(var19, this.connectToInetAddress(var12, var5, var14), var5.port(), var5.scheme(), var8.realm(), var8.scheme(), var5.url(), RequestorType.SERVER);
         }
      } while(var16 == null);

      String var11;
      if (var6) {
         var11 = "Proxy-Authorization";
      } else {
         var11 = "Authorization";
      }

      String var15 = var16.getUserName();
      Intrinsics.checkExpressionValueIsNotNull(var15, "auth.userName");
      char[] var17 = var16.getPassword();
      Intrinsics.checkExpressionValueIsNotNull(var17, "auth.password");
      var15 = Credentials.basic(var15, new String(var17), var8.charset());
      return var4.newBuilder().header(var11, var15).build();
   }
}
