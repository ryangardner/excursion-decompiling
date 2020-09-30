package okhttp3.internal.platform.android;

import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.ConscryptPlatform;
import okhttp3.internal.platform.Platform;
import org.conscrypt.Conscrypt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0012\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0011"},
   d2 = {"Lokhttp3/internal/platform/android/ConscryptSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ConscryptSocketAdapter implements SocketAdapter {
   public static final ConscryptSocketAdapter.Companion Companion = new ConscryptSocketAdapter.Companion((DefaultConstructorMarker)null);
   private static final DeferredSocketAdapter.Factory factory = (DeferredSocketAdapter.Factory)(new DeferredSocketAdapter.Factory() {
      public SocketAdapter create(SSLSocket var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
         return (SocketAdapter)(new ConscryptSocketAdapter());
      }

      public boolean matchesSocket(SSLSocket var1) {
         Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
         boolean var2;
         if (ConscryptPlatform.Companion.isSupported() && Conscrypt.isConscrypt(var1)) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }
   });

   public void configureTlsExtensions(SSLSocket var1, String var2, List<? extends Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (this.matchesSocket(var1)) {
         Conscrypt.setUseSessionTickets(var1, true);
         Object[] var4 = ((Collection)Platform.Companion.alpnProtocolNames(var3)).toArray(new String[0]);
         if (var4 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
         }

         Conscrypt.setApplicationProtocols(var1, (String[])var4);
      }

   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      String var2;
      if (this.matchesSocket(var1)) {
         var2 = Conscrypt.getApplicationProtocol(var1);
      } else {
         var2 = null;
      }

      return var2;
   }

   public boolean isSupported() {
      return ConscryptPlatform.Companion.isSupported();
   }

   public boolean matchesSocket(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return Conscrypt.isConscrypt(var1);
   }

   public boolean matchesSocketFactory(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.matchesSocketFactory(this, var1);
   }

   public X509TrustManager trustManager(SSLSocketFactory var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketFactory");
      return SocketAdapter.DefaultImpls.trustManager(this, var1);
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/platform/android/ConscryptSocketAdapter$Companion;", "", "()V", "factory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "okhttp"},
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

      public final DeferredSocketAdapter.Factory getFactory() {
         return ConscryptSocketAdapter.factory;
      }
   }
}
