package okhttp3.internal.platform;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Protocol;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0002\u0015\u0016B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u0007¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J(\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00102\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform;", "Lokhttp3/internal/platform/Platform;", "putMethod", "Ljava/lang/reflect/Method;", "getMethod", "removeMethod", "clientProviderClass", "Ljava/lang/Class;", "serverProviderClass", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/Class;Ljava/lang/Class;)V", "afterHandshake", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "configureTlsExtensions", "hostname", "", "protocols", "", "Lokhttp3/Protocol;", "getSelectedProtocol", "AlpnProvider", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Jdk8WithJettyBootPlatform extends Platform {
   public static final Jdk8WithJettyBootPlatform.Companion Companion = new Jdk8WithJettyBootPlatform.Companion((DefaultConstructorMarker)null);
   private final Class<?> clientProviderClass;
   private final Method getMethod;
   private final Method putMethod;
   private final Method removeMethod;
   private final Class<?> serverProviderClass;

   public Jdk8WithJettyBootPlatform(Method var1, Method var2, Method var3, Class<?> var4, Class<?> var5) {
      Intrinsics.checkParameterIsNotNull(var1, "putMethod");
      Intrinsics.checkParameterIsNotNull(var2, "getMethod");
      Intrinsics.checkParameterIsNotNull(var3, "removeMethod");
      Intrinsics.checkParameterIsNotNull(var4, "clientProviderClass");
      Intrinsics.checkParameterIsNotNull(var5, "serverProviderClass");
      super();
      this.putMethod = var1;
      this.getMethod = var2;
      this.removeMethod = var3;
      this.clientProviderClass = var4;
      this.serverProviderClass = var5;
   }

   public void afterHandshake(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");

      try {
         this.removeMethod.invoke((Object)null, var1);
      } catch (IllegalAccessException var2) {
         throw (Throwable)(new AssertionError("failed to remove ALPN", (Throwable)var2));
      } catch (InvocationTargetException var3) {
         throw (Throwable)(new AssertionError("failed to remove ALPN", (Throwable)var3));
      }
   }

   public void configureTlsExtensions(SSLSocket var1, String var2, List<? extends Protocol> var3) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      Intrinsics.checkParameterIsNotNull(var3, "protocols");
      List var4 = Platform.Companion.alpnProtocolNames(var3);

      try {
         ClassLoader var9 = Platform.class.getClassLoader();
         Class var11 = this.clientProviderClass;
         Class var5 = this.serverProviderClass;
         Jdk8WithJettyBootPlatform.AlpnProvider var6 = new Jdk8WithJettyBootPlatform.AlpnProvider(var4);
         InvocationHandler var12 = (InvocationHandler)var6;
         Object var10 = Proxy.newProxyInstance(var9, new Class[]{var11, var5}, var12);
         this.putMethod.invoke((Object)null, var1, var10);
      } catch (InvocationTargetException var7) {
         throw (Throwable)(new AssertionError("failed to set ALPN", (Throwable)var7));
      } catch (IllegalAccessException var8) {
         throw (Throwable)(new AssertionError("failed to set ALPN", (Throwable)var8));
      }
   }

   public String getSelectedProtocol(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");

      InvocationTargetException var22;
      label71: {
         IllegalAccessException var10000;
         label75: {
            Method var2;
            boolean var10001;
            try {
               var2 = this.getMethod;
            } catch (InvocationTargetException var14) {
               var22 = var14;
               var10001 = false;
               break label71;
            } catch (IllegalAccessException var15) {
               var10000 = var15;
               var10001 = false;
               break label75;
            }

            Object var3 = null;

            InvocationHandler var16;
            try {
               var16 = Proxy.getInvocationHandler(var2.invoke((Object)null, var1));
            } catch (InvocationTargetException var12) {
               var22 = var12;
               var10001 = false;
               break label71;
            } catch (IllegalAccessException var13) {
               var10000 = var13;
               var10001 = false;
               break label75;
            }

            if (var16 != null) {
               label80: {
                  Jdk8WithJettyBootPlatform.AlpnProvider var17;
                  try {
                     var17 = (Jdk8WithJettyBootPlatform.AlpnProvider)var16;
                     if (!var17.getUnsupported$okhttp() && var17.getSelected$okhttp() == null) {
                        Platform.log$default(this, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", 0, (Throwable)null, 6, (Object)null);
                        return null;
                     }
                  } catch (InvocationTargetException var8) {
                     var22 = var8;
                     var10001 = false;
                     break label71;
                  } catch (IllegalAccessException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label80;
                  }

                  String var18;
                  label81: {
                     try {
                        if (!var17.getUnsupported$okhttp()) {
                           break label81;
                        }
                     } catch (InvocationTargetException var6) {
                        var22 = var6;
                        var10001 = false;
                        break label71;
                     } catch (IllegalAccessException var7) {
                        var10000 = var7;
                        var10001 = false;
                        break label80;
                     }

                     var18 = (String)var3;
                     return var18;
                  }

                  try {
                     var18 = var17.getSelected$okhttp();
                  } catch (InvocationTargetException var4) {
                     var22 = var4;
                     var10001 = false;
                     break label71;
                  } catch (IllegalAccessException var5) {
                     var10000 = var5;
                     var10001 = false;
                     break label80;
                  }

                  return var18;
               }
            } else {
               try {
                  TypeCastException var21 = new TypeCastException("null cannot be cast to non-null type okhttp3.internal.platform.Jdk8WithJettyBootPlatform.AlpnProvider");
                  throw var21;
               } catch (InvocationTargetException var10) {
                  var22 = var10;
                  var10001 = false;
                  break label71;
               } catch (IllegalAccessException var11) {
                  var10000 = var11;
                  var10001 = false;
               }
            }
         }

         IllegalAccessException var19 = var10000;
         throw (Throwable)(new AssertionError("failed to get ALPN selected protocol", (Throwable)var19));
      }

      InvocationTargetException var20 = var22;
      throw (Throwable)(new AssertionError("failed to get ALPN selected protocol", (Throwable)var20));
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J0\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0017H\u0096\u0002¢\u0006\u0002\u0010\u0018R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0004X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u0019"},
      d2 = {"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$AlpnProvider;", "Ljava/lang/reflect/InvocationHandler;", "protocols", "", "", "(Ljava/util/List;)V", "selected", "getSelected$okhttp", "()Ljava/lang/String;", "setSelected$okhttp", "(Ljava/lang/String;)V", "unsupported", "", "getUnsupported$okhttp", "()Z", "setUnsupported$okhttp", "(Z)V", "invoke", "", "proxy", "method", "Ljava/lang/reflect/Method;", "args", "", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   private static final class AlpnProvider implements InvocationHandler {
      private final List<String> protocols;
      private String selected;
      private boolean unsupported;

      public AlpnProvider(List<String> var1) {
         Intrinsics.checkParameterIsNotNull(var1, "protocols");
         super();
         this.protocols = var1;
      }

      public final String getSelected$okhttp() {
         return this.selected;
      }

      public final boolean getUnsupported$okhttp() {
         return this.unsupported;
      }

      public Object invoke(Object var1, Method var2, Object[] var3) throws Throwable {
         Intrinsics.checkParameterIsNotNull(var1, "proxy");
         Intrinsics.checkParameterIsNotNull(var2, "method");
         if (var3 == null) {
            var3 = new Object[0];
         }

         String var7 = var2.getName();
         Class var4 = var2.getReturnType();
         if (Intrinsics.areEqual((Object)var7, (Object)"supports") && Intrinsics.areEqual((Object)Boolean.TYPE, (Object)var4)) {
            return true;
         } else if (Intrinsics.areEqual((Object)var7, (Object)"unsupported") && Intrinsics.areEqual((Object)Void.TYPE, (Object)var4)) {
            this.unsupported = true;
            return null;
         } else {
            if (Intrinsics.areEqual((Object)var7, (Object)"protocols")) {
               boolean var5;
               if (var3.length == 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               if (var5) {
                  return this.protocols;
               }
            }

            if ((Intrinsics.areEqual((Object)var7, (Object)"selectProtocol") || Intrinsics.areEqual((Object)var7, (Object)"select")) && Intrinsics.areEqual((Object)String.class, (Object)var4) && var3.length == 1 && var3[0] instanceof List) {
               var1 = var3[0];
               if (var1 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.List<*>");
               } else {
                  List var8 = (List)var1;
                  int var6 = var8.size();
                  if (var6 >= 0) {
                     int var11 = 0;

                     while(true) {
                        Object var9 = var8.get(var11);
                        if (var9 == null) {
                           throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
                        }

                        String var10 = (String)var9;
                        if (this.protocols.contains(var10)) {
                           this.selected = var10;
                           return var10;
                        }

                        if (var11 == var6) {
                           break;
                        }

                        ++var11;
                     }
                  }

                  var7 = (String)this.protocols.get(0);
                  this.selected = var7;
                  return var7;
               }
            } else if ((Intrinsics.areEqual((Object)var7, (Object)"protocolSelected") || Intrinsics.areEqual((Object)var7, (Object)"selected")) && var3.length == 1) {
               var1 = var3[0];
               if (var1 != null) {
                  this.selected = (String)var1;
                  return null;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
               }
            } else {
               return var2.invoke(this, Arrays.copyOf(var3, var3.length));
            }
         }
      }

      public final void setSelected$okhttp(String var1) {
         this.selected = var1;
      }

      public final void setUnsupported$okhttp(boolean var1) {
         this.unsupported = var1;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¨\u0006\u0005"},
      d2 = {"Lokhttp3/internal/platform/Jdk8WithJettyBootPlatform$Companion;", "", "()V", "buildIfSupported", "Lokhttp3/internal/platform/Platform;", "okhttp"},
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

      public final Platform buildIfSupported() {
         String var1 = System.getProperty("java.specification.version", "unknown");

         label19: {
            int var2;
            try {
               Intrinsics.checkExpressionValueIsNotNull(var1, "jvmVersion");
               var2 = Integer.parseInt(var1);
            } catch (NumberFormatException var9) {
               break label19;
            }

            if (var2 >= 9) {
               return null;
            }
         }

         try {
            Class var10 = Class.forName("org.eclipse.jetty.alpn.ALPN", true, (ClassLoader)null);
            StringBuilder var3 = new StringBuilder();
            var3.append("org.eclipse.jetty.alpn.ALPN");
            var3.append("$Provider");
            Class var4 = Class.forName(var3.toString(), true, (ClassLoader)null);
            var3 = new StringBuilder();
            var3.append("org.eclipse.jetty.alpn.ALPN");
            var3.append("$ClientProvider");
            Class var13 = Class.forName(var3.toString(), true, (ClassLoader)null);
            StringBuilder var5 = new StringBuilder();
            var5.append("org.eclipse.jetty.alpn.ALPN");
            var5.append("$ServerProvider");
            Class var15 = Class.forName(var5.toString(), true, (ClassLoader)null);
            Method var14 = var10.getMethod("put", SSLSocket.class, var4);
            Method var6 = var10.getMethod("get", SSLSocket.class);
            Method var11 = var10.getMethod("remove", SSLSocket.class);
            Intrinsics.checkExpressionValueIsNotNull(var14, "putMethod");
            Intrinsics.checkExpressionValueIsNotNull(var6, "getMethod");
            Intrinsics.checkExpressionValueIsNotNull(var11, "removeMethod");
            Intrinsics.checkExpressionValueIsNotNull(var13, "clientProviderClass");
            Intrinsics.checkExpressionValueIsNotNull(var15, "serverProviderClass");
            Jdk8WithJettyBootPlatform var7 = new Jdk8WithJettyBootPlatform(var14, var6, var11, var13, var15);
            Platform var12 = (Platform)var7;
            return var12;
         } catch (NoSuchMethodException | ClassNotFoundException var8) {
            return null;
         }
      }
   }
}
