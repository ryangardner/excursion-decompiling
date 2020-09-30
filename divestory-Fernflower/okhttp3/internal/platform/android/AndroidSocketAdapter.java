package okhttp3.internal.platform.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Protocol;
import okhttp3.internal.platform.AndroidPlatform;
import okhttp3.internal.platform.Platform;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J(\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u000e\u001a\u00020\u0004H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u0004H\u0016R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0002\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"},
   d2 = {"Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "Lokhttp3/internal/platform/android/SocketAdapter;", "sslSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "(Ljava/lang/Class;)V", "getAlpnSelectedProtocol", "Ljava/lang/reflect/Method;", "kotlin.jvm.PlatformType", "setAlpnProtocols", "setHostname", "setUseSessionTickets", "configureTlsExtensions", "", "sslSocket", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "isSupported", "", "matchesSocket", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public class AndroidSocketAdapter implements SocketAdapter {
   public static final AndroidSocketAdapter.Companion Companion;
   private static final DeferredSocketAdapter.Factory playProviderFactory;
   private final Method getAlpnSelectedProtocol;
   private final Method setAlpnProtocols;
   private final Method setHostname;
   private final Method setUseSessionTickets;
   private final Class<? super SSLSocket> sslSocketClass;

   static {
      AndroidSocketAdapter.Companion var0 = new AndroidSocketAdapter.Companion((DefaultConstructorMarker)null);
      Companion = var0;
      playProviderFactory = var0.factory("com.google.android.gms.org.conscrypt");
   }

   public AndroidSocketAdapter(Class<? super SSLSocket> var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocketClass");
      super();
      this.sslSocketClass = var1;
      Method var2 = var1.getDeclaredMethod("setUseSessionTickets", Boolean.TYPE);
      Intrinsics.checkExpressionValueIsNotNull(var2, "sslSocketClass.getDeclar…:class.javaPrimitiveType)");
      this.setUseSessionTickets = var2;
      this.setHostname = this.sslSocketClass.getMethod("setHostname", String.class);
      this.getAlpnSelectedProtocol = this.sslSocketClass.getMethod("getAlpnSelectedProtocol");
      this.setAlpnProtocols = this.sslSocketClass.getMethod("setAlpnProtocols", byte[].class);
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<? extends Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      if (this.matchesSocket(var1)) {
         InvocationTargetException var10000;
         label39: {
            IllegalAccessException var12;
            label44: {
               boolean var10001;
               try {
                  this.setUseSessionTickets.invoke(var1, true);
               } catch (IllegalAccessException var8) {
                  var12 = var8;
                  var10001 = false;
                  break label44;
               } catch (InvocationTargetException var9) {
                  var10000 = var9;
                  var10001 = false;
                  break label39;
               }

               if (var2 != null) {
                  try {
                     this.setHostname.invoke(var1, var2);
                  } catch (IllegalAccessException var6) {
                     var12 = var6;
                     var10001 = false;
                     break label44;
                  } catch (InvocationTargetException var7) {
                     var10000 = var7;
                     var10001 = false;
                     break label39;
                  }
               }

               try {
                  this.setAlpnProtocols.invoke(var1, Platform.Companion.concatLengthPrefixed(var3));
                  return;
               } catch (IllegalAccessException var4) {
                  var12 = var4;
                  var10001 = false;
               } catch (InvocationTargetException var5) {
                  var10000 = var5;
                  var10001 = false;
                  break label39;
               }
            }

            IllegalAccessException var11 = var12;
            throw (Throwable)(new AssertionError(var11));
         }

         InvocationTargetException var10 = var10000;
         throw (Throwable)(new AssertionError(var10));
      }
   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      boolean var2 = this.matchesSocket(var1);
      Object var3 = null;
      if (!var2) {
         return null;
      } else {
         IllegalAccessException var16;
         label42: {
            InvocationTargetException var10000;
            label59: {
               String var12;
               NullPointerException var17;
               label49: {
                  byte[] var4;
                  boolean var10001;
                  try {
                     var4 = (byte[])this.getAlpnSelectedProtocol.invoke(var1);
                  } catch (NullPointerException var9) {
                     var17 = var9;
                     var10001 = false;
                     break label49;
                  } catch (IllegalAccessException var10) {
                     var16 = var10;
                     var10001 = false;
                     break label42;
                  } catch (InvocationTargetException var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label59;
                  }

                  var12 = (String)var3;
                  if (var4 == null) {
                     return var12;
                  }

                  try {
                     Charset var5 = StandardCharsets.UTF_8;
                     Intrinsics.checkExpressionValueIsNotNull(var5, "StandardCharsets.UTF_8");
                     var12 = new String(var4, var5);
                     return var12;
                  } catch (NullPointerException var6) {
                     var17 = var6;
                     var10001 = false;
                  } catch (IllegalAccessException var7) {
                     var16 = var7;
                     var10001 = false;
                     break label42;
                  } catch (InvocationTargetException var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label59;
                  }
               }

               NullPointerException var15 = var17;
               if (!Intrinsics.areEqual((Object)var15.getMessage(), (Object)"ssl == null")) {
                  throw (Throwable)var15;
               }

               var12 = (String)var3;
               return var12;
            }

            InvocationTargetException var13 = var10000;
            throw (Throwable)(new AssertionError(var13));
         }

         IllegalAccessException var14 = var16;
         throw (Throwable)(new AssertionError(var14));
      }
   }

   public boolean isSupported() {
      return AndroidPlatform.Companion.isSupported();
   }

   public boolean matchesSocket(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      return this.sslSocketClass.isInstance(var1);
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
      d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u000b0\nH\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"},
      d2 = {"Lokhttp3/internal/platform/android/AndroidSocketAdapter$Companion;", "", "()V", "playProviderFactory", "Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "getPlayProviderFactory", "()Lokhttp3/internal/platform/android/DeferredSocketAdapter$Factory;", "build", "Lokhttp3/internal/platform/android/AndroidSocketAdapter;", "actualSSLSocketClass", "Ljava/lang/Class;", "Ljavax/net/ssl/SSLSocket;", "factory", "packageName", "", "okhttp"},
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

      private final AndroidSocketAdapter build(Class<? super SSLSocket> var1) {
         Class var2 = var1;

         while(var2 != null && Intrinsics.areEqual((Object)var2.getSimpleName(), (Object)"OpenSSLSocketImpl") ^ true) {
            var2 = var2.getSuperclass();
            if (var2 == null) {
               StringBuilder var3 = new StringBuilder();
               var3.append("No OpenSSLSocketImpl superclass of socket of type ");
               var3.append(var1);
               throw (Throwable)(new AssertionError(var3.toString()));
            }
         }

         if (var2 == null) {
            Intrinsics.throwNpe();
         }

         return new AndroidSocketAdapter(var2);
      }

      public final DeferredSocketAdapter.Factory factory(final String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "packageName");
         return (DeferredSocketAdapter.Factory)(new DeferredSocketAdapter.Factory() {
            public SocketAdapter create(SSLSocket var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "sslSocket");
               return (SocketAdapter)AndroidSocketAdapter.Companion.build(var1x.getClass());
            }

            public boolean matchesSocket(SSLSocket var1x) {
               Intrinsics.checkParameterIsNotNull(var1x, "sslSocket");
               String var2 = var1x.getClass().getName();
               Intrinsics.checkExpressionValueIsNotNull(var2, "sslSocket.javaClass.name");
               StringBuilder var3 = new StringBuilder();
               var3.append(var1);
               var3.append('.');
               return StringsKt.startsWith$default(var2, var3.toString(), false, 2, (Object)null);
            }
         });
      }

      public final DeferredSocketAdapter.Factory getPlayProviderFactory() {
         return AndroidSocketAdapter.playProviderFactory;
      }
   }
}
