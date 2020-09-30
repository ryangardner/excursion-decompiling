package okhttp3.internal.platform.android;

import android.net.ssl.SSLSockets;
import android.os.Build.VERSION;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;
import okhttp3.internal.platform.Platform;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0017J\u0012\u0010\f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0017J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0011"},
   d2 = {"Lokhttp3/internal/platform/android/Android10SocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "()V", "configureTlsExtensions", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Android10SocketAdapter implements SocketAdapter {
   public static final Android10SocketAdapter.Companion Companion = new Android10SocketAdapter.Companion((DefaultConstructorMarker)null);

   public void configureTlsExtensions(SSLSocket var1, String var2, List<? extends Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");

      IllegalArgumentException var10000;
      label26: {
         boolean var10001;
         SSLParameters var9;
         Object[] var10;
         try {
            SSLSockets.setUseSessionTickets(var1, true);
            var9 = var1.getSSLParameters();
            Intrinsics.checkExpressionValueIsNotNull(var9, "sslParameters");
            var10 = ((Collection)Platform.Companion.alpnProtocolNames(var3)).toArray(new String[0]);
         } catch (IllegalArgumentException var6) {
            var10000 = var6;
            var10001 = false;
            break label26;
         }

         if (var10 != null) {
            try {
               var9.setApplicationProtocols((String[])var10);
               var1.setSSLParameters(var9);
               return;
            } catch (IllegalArgumentException var4) {
               var10000 = var4;
               var10001 = false;
            }
         } else {
            try {
               TypeCastException var8 = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
               throw var8;
            } catch (IllegalArgumentException var5) {
               var10000 = var5;
               var10001 = false;
            }
         }
      }

      IllegalArgumentException var7 = var10000;
      throw (Throwable)(new IOException("Android internal error", (Throwable)var7));
   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      String var2 = var1.getApplicationProtocol();
      String var3;
      if (var2 != null) {
         var3 = var2;
         if (!Intrinsics.areEqual((Object)var2, (Object)"")) {
            return var3;
         }
      }

      var3 = null;
      return var3;
   }

   public boolean isSupported() {
      return Companion.isSupported();
   }

   public boolean matchesSocket(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return SSLSockets.isSupportedSocket(var1);
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
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0087\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"},
      d2 = {"Lokhttp3/internal/platform/android/Android10SocketAdapter$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/android/SocketAdapter;", "isSupported", "", "okhttp"},
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

      public final SocketAdapter buildIfSupported() {
         SocketAdapter var1;
         if (((Android10SocketAdapter.Companion)this).isSupported()) {
            var1 = (SocketAdapter)(new Android10SocketAdapter());
         } else {
            var1 = null;
         }

         return var1;
      }

      public final boolean isSupported() {
         boolean var1;
         if (Platform.Companion.isAndroid() && VERSION.SDK_INT >= 29) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }
   }
}
