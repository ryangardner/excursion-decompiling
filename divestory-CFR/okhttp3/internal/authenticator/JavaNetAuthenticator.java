/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.authenticator;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
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
import okhttp3.internal.authenticator.JavaNetAuthenticator$WhenMappings;

@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f*\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0003H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lokhttp3/internal/authenticator/JavaNetAuthenticator;", "Lokhttp3/Authenticator;", "defaultDns", "Lokhttp3/Dns;", "(Lokhttp3/Dns;)V", "authenticate", "Lokhttp3/Request;", "route", "Lokhttp3/Route;", "response", "Lokhttp3/Response;", "connectToInetAddress", "Ljava/net/InetAddress;", "Ljava/net/Proxy;", "url", "Lokhttp3/HttpUrl;", "dns", "okhttp"}, k=1, mv={1, 1, 16})
public final class JavaNetAuthenticator
implements Authenticator {
    private final Dns defaultDns;

    public JavaNetAuthenticator() {
        this(null, 1, null);
    }

    public JavaNetAuthenticator(Dns dns) {
        Intrinsics.checkParameterIsNotNull(dns, "defaultDns");
        this.defaultDns = dns;
    }

    public /* synthetic */ JavaNetAuthenticator(Dns dns, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            dns = Dns.SYSTEM;
        }
        this(dns);
    }

    private final InetAddress connectToInetAddress(Proxy object, HttpUrl httpUrl, Dns dns) throws IOException {
        Proxy.Type type = ((Proxy)object).type();
        if (type != null && JavaNetAuthenticator$WhenMappings.$EnumSwitchMapping$0[type.ordinal()] == 1) {
            return CollectionsKt.first(dns.lookup(httpUrl.host()));
        }
        object = ((Proxy)object).address();
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
        object = ((InetSocketAddress)object).getAddress();
        Intrinsics.checkExpressionValueIsNotNull(object, "(address() as InetSocketAddress).address");
        return object;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public Request authenticate(Route var1_1, Response var2_2) throws IOException {
        Intrinsics.checkParameterIsNotNull(var2_2, "response");
        var3_3 = var2_2.challenges();
        var4_4 = var2_2.request();
        var5_5 = var4_4.url();
        var6_6 = var2_2.code() == 407;
        if (var1_1 == null || (var2_2 = var1_1.proxy()) == null) {
            var2_2 = Proxy.NO_PROXY;
        }
        var7_7 = var3_3.iterator();
        do lbl-1000: // 3 sources:
        {
            if (var7_7.hasNext() == false) return null;
            var8_8 = var7_7.next();
            if (!StringsKt.equals("Basic", var8_8.scheme(), true)) ** GOTO lbl-1000
            if (var1_1 == null || (var3_3 = var1_1.address()) == null || (var3_3 = var3_3.dns()) == null) {
                var3_3 = this.defaultDns;
            }
            if (var6_6) {
                var9_9 = var2_2.address();
                if (var9_9 == null) throw new TypeCastException("null cannot be cast to non-null type java.net.InetSocketAddress");
                var9_9 = (InetSocketAddress)var9_9;
                var10_10 = var9_9.getHostName();
                Intrinsics.checkExpressionValueIsNotNull(var2_2, "proxy");
                var3_3 = java.net.Authenticator.requestPasswordAuthentication(var10_10, this.connectToInetAddress((Proxy)var2_2, var5_5, (Dns)var3_3), var9_9.getPort(), var5_5.scheme(), var8_8.realm(), var8_8.scheme(), var5_5.url(), Authenticator.RequestorType.PROXY);
                continue;
            }
            var9_9 = var5_5.host();
            Intrinsics.checkExpressionValueIsNotNull(var2_2, "proxy");
            var3_3 = java.net.Authenticator.requestPasswordAuthentication((String)var9_9, this.connectToInetAddress((Proxy)var2_2, var5_5, (Dns)var3_3), var5_5.port(), var5_5.scheme(), var8_8.realm(), var8_8.scheme(), var5_5.url(), Authenticator.RequestorType.SERVER);
        } while (var3_3 == null);
        var1_1 = var6_6 != false ? "Proxy-Authorization" : "Authorization";
        var2_2 = var3_3.getUserName();
        Intrinsics.checkExpressionValueIsNotNull(var2_2, "auth.userName");
        var3_3 = var3_3.getPassword();
        Intrinsics.checkExpressionValueIsNotNull(var3_3, "auth.password");
        var2_2 = Credentials.basic((String)var2_2, new String(var3_3), var8_8.charset());
        return var4_4.newBuilder().header((String)var1_1, (String)var2_2).build();
    }
}

