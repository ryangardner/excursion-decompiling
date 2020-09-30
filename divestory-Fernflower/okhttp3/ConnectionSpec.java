package okhttp3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLSocket;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\t\u0018\u0000 $2\u00020\u0001:\u0002#$B7\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006¢\u0006\u0002\u0010\tJ\u001d\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0000¢\u0006\u0002\b\u0017J\u0015\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0007¢\u0006\u0002\b\u0018J\u0013\u0010\u0019\u001a\u00020\u00032\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\u000e\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001e\u001a\u00020\u0015J\u0018\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0003H\u0002J\r\u0010\u0004\u001a\u00020\u0003H\u0007¢\u0006\u0002\b J\u0015\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u000bH\u0007¢\u0006\u0002\b!J\b\u0010\"\u001a\u00020\u0007H\u0016R\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b8G¢\u0006\u0006\u001a\u0004\b\n\u0010\rR\u0018\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000eR\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000fR\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u000b8G¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000e¨\u0006%"},
   d2 = {"Lokhttp3/ConnectionSpec;", "", "isTls", "", "supportsTlsExtensions", "cipherSuitesAsString", "", "", "tlsVersionsAsString", "(ZZ[Ljava/lang/String;[Ljava/lang/String;)V", "cipherSuites", "", "Lokhttp3/CipherSuite;", "()Ljava/util/List;", "[Ljava/lang/String;", "()Z", "tlsVersions", "Lokhttp3/TlsVersion;", "apply", "", "sslSocket", "Ljavax/net/ssl/SSLSocket;", "isFallback", "apply$okhttp", "-deprecated_cipherSuites", "equals", "other", "hashCode", "", "isCompatible", "socket", "supportedSpec", "-deprecated_supportsTlsExtensions", "-deprecated_tlsVersions", "toString", "Builder", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class ConnectionSpec {
   private static final CipherSuite[] APPROVED_CIPHER_SUITES;
   public static final ConnectionSpec CLEARTEXT;
   public static final ConnectionSpec COMPATIBLE_TLS;
   public static final ConnectionSpec.Companion Companion = new ConnectionSpec.Companion((DefaultConstructorMarker)null);
   public static final ConnectionSpec MODERN_TLS;
   private static final CipherSuite[] RESTRICTED_CIPHER_SUITES;
   public static final ConnectionSpec RESTRICTED_TLS;
   private final String[] cipherSuitesAsString;
   private final boolean isTls;
   private final boolean supportsTlsExtensions;
   private final String[] tlsVersionsAsString;

   static {
      RESTRICTED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256};
      APPROVED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_AES_128_GCM_SHA256, CipherSuite.TLS_AES_256_GCM_SHA384, CipherSuite.TLS_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
      ConnectionSpec.Builder var0 = new ConnectionSpec.Builder(true);
      CipherSuite[] var1 = RESTRICTED_CIPHER_SUITES;
      RESTRICTED_TLS = var0.cipherSuites((CipherSuite[])Arrays.copyOf(var1, var1.length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
      ConnectionSpec.Builder var3 = new ConnectionSpec.Builder(true);
      CipherSuite[] var2 = APPROVED_CIPHER_SUITES;
      MODERN_TLS = var3.cipherSuites((CipherSuite[])Arrays.copyOf(var2, var2.length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
      var3 = new ConnectionSpec.Builder(true);
      var2 = APPROVED_CIPHER_SUITES;
      COMPATIBLE_TLS = var3.cipherSuites((CipherSuite[])Arrays.copyOf(var2, var2.length)).tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
      CLEARTEXT = (new ConnectionSpec.Builder(false)).build();
   }

   public ConnectionSpec(boolean var1, boolean var2, String[] var3, String[] var4) {
      this.isTls = var1;
      this.supportsTlsExtensions = var2;
      this.cipherSuitesAsString = var3;
      this.tlsVersionsAsString = var4;
   }

   private final ConnectionSpec supportedSpec(SSLSocket var1, boolean var2) {
      String[] var3;
      if (this.cipherSuitesAsString != null) {
         var3 = var1.getEnabledCipherSuites();
         Intrinsics.checkExpressionValueIsNotNull(var3, "sslSocket.enabledCipherSuites");
         var3 = Util.intersect(var3, this.cipherSuitesAsString, CipherSuite.Companion.getORDER_BY_NAME$okhttp());
      } else {
         var3 = var1.getEnabledCipherSuites();
      }

      String[] var4;
      if (this.tlsVersionsAsString != null) {
         var4 = var1.getEnabledProtocols();
         Intrinsics.checkExpressionValueIsNotNull(var4, "sslSocket.enabledProtocols");
         var4 = Util.intersect(var4, this.tlsVersionsAsString, ComparisonsKt.naturalOrder());
      } else {
         var4 = var1.getEnabledProtocols();
      }

      String[] var5 = var1.getSupportedCipherSuites();
      Intrinsics.checkExpressionValueIsNotNull(var5, "supportedCipherSuites");
      int var6 = Util.indexOf(var5, "TLS_FALLBACK_SCSV", CipherSuite.Companion.getORDER_BY_NAME$okhttp());
      String[] var7 = var3;
      if (var2) {
         var7 = var3;
         if (var6 != -1) {
            Intrinsics.checkExpressionValueIsNotNull(var3, "cipherSuitesIntersection");
            String var8 = var5[var6];
            Intrinsics.checkExpressionValueIsNotNull(var8, "supportedCipherSuites[indexOfFallbackScsv]");
            var7 = Util.concat(var3, var8);
         }
      }

      ConnectionSpec.Builder var10 = new ConnectionSpec.Builder(this);
      Intrinsics.checkExpressionValueIsNotNull(var7, "cipherSuitesIntersection");
      ConnectionSpec.Builder var9 = var10.cipherSuites((String[])Arrays.copyOf(var7, var7.length));
      Intrinsics.checkExpressionValueIsNotNull(var4, "tlsVersionsIntersection");
      return var9.tlsVersions((String[])Arrays.copyOf(var4, var4.length)).build();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cipherSuites",
   imports = {}
)
   )
   public final List<CipherSuite> _deprecated_cipherSuites/* $FF was: -deprecated_cipherSuites*/() {
      return this.cipherSuites();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "supportsTlsExtensions",
   imports = {}
)
   )
   public final boolean _deprecated_supportsTlsExtensions/* $FF was: -deprecated_supportsTlsExtensions*/() {
      return this.supportsTlsExtensions;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "tlsVersions",
   imports = {}
)
   )
   public final List<TlsVersion> _deprecated_tlsVersions/* $FF was: -deprecated_tlsVersions*/() {
      return this.tlsVersions();
   }

   public final void apply$okhttp(SSLSocket var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "sslSocket");
      ConnectionSpec var3 = this.supportedSpec(var1, var2);
      if (var3.tlsVersions() != null) {
         var1.setEnabledProtocols(var3.tlsVersionsAsString);
      }

      if (var3.cipherSuites() != null) {
         var1.setEnabledCipherSuites(var3.cipherSuitesAsString);
      }

   }

   public final List<CipherSuite> cipherSuites() {
      String[] var1 = this.cipherSuitesAsString;
      List var6;
      if (var1 != null) {
         Collection var2 = (Collection)(new ArrayList(var1.length));
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var1[var4];
            var2.add(CipherSuite.Companion.forJavaName(var5));
         }

         var6 = CollectionsKt.toList((Iterable)((List)var2));
      } else {
         var6 = null;
      }

      return var6;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof ConnectionSpec)) {
         return false;
      } else if (var1 == (ConnectionSpec)this) {
         return true;
      } else {
         boolean var2 = this.isTls;
         ConnectionSpec var3 = (ConnectionSpec)var1;
         if (var2 != var3.isTls) {
            return false;
         } else {
            if (var2) {
               if (!Arrays.equals(this.cipherSuitesAsString, var3.cipherSuitesAsString)) {
                  return false;
               }

               if (!Arrays.equals(this.tlsVersionsAsString, var3.tlsVersionsAsString)) {
                  return false;
               }

               if (this.supportsTlsExtensions != var3.supportsTlsExtensions) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int var3;
      if (this.isTls) {
         String[] var1 = this.cipherSuitesAsString;
         int var2 = 0;
         if (var1 != null) {
            var3 = Arrays.hashCode(var1);
         } else {
            var3 = 0;
         }

         var1 = this.tlsVersionsAsString;
         if (var1 != null) {
            var2 = Arrays.hashCode(var1);
         }

         var3 = ((527 + var3) * 31 + var2) * 31 + (this.supportsTlsExtensions ^ 1);
      } else {
         var3 = 17;
      }

      return var3;
   }

   public final boolean isCompatible(SSLSocket var1) {
      Intrinsics.checkParameterIsNotNull(var1, "socket");
      if (!this.isTls) {
         return false;
      } else {
         String[] var2 = this.tlsVersionsAsString;
         if (var2 != null && !Util.hasIntersection(var2, var1.getEnabledProtocols(), ComparisonsKt.naturalOrder())) {
            return false;
         } else {
            var2 = this.cipherSuitesAsString;
            return var2 == null || Util.hasIntersection(var2, var1.getEnabledCipherSuites(), CipherSuite.Companion.getORDER_BY_NAME$okhttp());
         }
      }
   }

   public final boolean isTls() {
      return this.isTls;
   }

   public final boolean supportsTlsExtensions() {
      return this.supportsTlsExtensions;
   }

   public final List<TlsVersion> tlsVersions() {
      String[] var1 = this.tlsVersionsAsString;
      List var6;
      if (var1 != null) {
         Collection var2 = (Collection)(new ArrayList(var1.length));
         int var3 = var1.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var1[var4];
            var2.add(TlsVersion.Companion.forJavaName(var5));
         }

         var6 = CollectionsKt.toList((Iterable)((List)var2));
      } else {
         var6 = null;
      }

      return var6;
   }

   public String toString() {
      if (!this.isTls) {
         return "ConnectionSpec()";
      } else {
         StringBuilder var1 = new StringBuilder();
         var1.append("ConnectionSpec(");
         var1.append("cipherSuites=");
         var1.append(Objects.toString(this.cipherSuites(), "[all enabled]"));
         var1.append(", ");
         var1.append("tlsVersions=");
         var1.append(Objects.toString(this.tlsVersions(), "[all enabled]"));
         var1.append(", ");
         var1.append("supportsTlsExtensions=");
         var1.append(this.supportsTlsExtensions);
         var1.append(')');
         return var1.toString();
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u0019\u001a\u00020\u0000J\u0006\u0010\u001a\u001a\u00020\u0000J\u0006\u0010\u001b\u001a\u00020\u0006J\u001f\u0010\b\u001a\u00020\u00002\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n¢\u0006\u0002\u0010\u001cJ\u001f\u0010\b\u001a\u00020\u00002\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001d0\t\"\u00020\u001d¢\u0006\u0002\u0010\u001eJ\u0010\u0010\u0010\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0003H\u0007J\u001f\u0010\u0016\u001a\u00020\u00002\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\t\"\u00020\n¢\u0006\u0002\u0010\u001cJ\u001f\u0010\u0016\u001a\u00020\u00002\u0012\u0010\u0016\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001f0\t\"\u00020\u001f¢\u0006\u0002\u0010 R$\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0080\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0010\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0004R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0012\"\u0004\b\u0015\u0010\u0004R$\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tX\u0080\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u000e¨\u0006!"},
      d2 = {"Lokhttp3/ConnectionSpec$Builder;", "", "tls", "", "(Z)V", "connectionSpec", "Lokhttp3/ConnectionSpec;", "(Lokhttp3/ConnectionSpec;)V", "cipherSuites", "", "", "getCipherSuites$okhttp", "()[Ljava/lang/String;", "setCipherSuites$okhttp", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "supportsTlsExtensions", "getSupportsTlsExtensions$okhttp", "()Z", "setSupportsTlsExtensions$okhttp", "getTls$okhttp", "setTls$okhttp", "tlsVersions", "getTlsVersions$okhttp", "setTlsVersions$okhttp", "allEnabledCipherSuites", "allEnabledTlsVersions", "build", "([Ljava/lang/String;)Lokhttp3/ConnectionSpec$Builder;", "Lokhttp3/CipherSuite;", "([Lokhttp3/CipherSuite;)Lokhttp3/ConnectionSpec$Builder;", "Lokhttp3/TlsVersion;", "([Lokhttp3/TlsVersion;)Lokhttp3/ConnectionSpec$Builder;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private String[] cipherSuites;
      private boolean supportsTlsExtensions;
      private boolean tls;
      private String[] tlsVersions;

      public Builder(ConnectionSpec var1) {
         Intrinsics.checkParameterIsNotNull(var1, "connectionSpec");
         super();
         this.tls = var1.isTls();
         this.cipherSuites = var1.cipherSuitesAsString;
         this.tlsVersions = var1.tlsVersionsAsString;
         this.supportsTlsExtensions = var1.supportsTlsExtensions();
      }

      public Builder(boolean var1) {
         this.tls = var1;
      }

      public final ConnectionSpec.Builder allEnabledCipherSuites() {
         ConnectionSpec.Builder var1 = (ConnectionSpec.Builder)this;
         if (var1.tls) {
            var1.cipherSuites = (String[])null;
            return var1;
         } else {
            throw (Throwable)(new IllegalArgumentException("no cipher suites for cleartext connections".toString()));
         }
      }

      public final ConnectionSpec.Builder allEnabledTlsVersions() {
         ConnectionSpec.Builder var1 = (ConnectionSpec.Builder)this;
         if (var1.tls) {
            var1.tlsVersions = (String[])null;
            return var1;
         } else {
            throw (Throwable)(new IllegalArgumentException("no TLS versions for cleartext connections".toString()));
         }
      }

      public final ConnectionSpec build() {
         return new ConnectionSpec(this.tls, this.supportsTlsExtensions, this.cipherSuites, this.tlsVersions);
      }

      public final ConnectionSpec.Builder cipherSuites(String... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "cipherSuites");
         ConnectionSpec.Builder var2 = (ConnectionSpec.Builder)this;
         if (var2.tls) {
            boolean var3;
            if (var1.length == 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3 ^ true) {
               Object var4 = var1.clone();
               if (var4 != null) {
                  var2.cipherSuites = (String[])var4;
                  return var2;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
               }
            } else {
               throw (Throwable)(new IllegalArgumentException("At least one cipher suite is required".toString()));
            }
         } else {
            throw (Throwable)(new IllegalArgumentException("no cipher suites for cleartext connections".toString()));
         }
      }

      public final ConnectionSpec.Builder cipherSuites(CipherSuite... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "cipherSuites");
         ConnectionSpec.Builder var2 = (ConnectionSpec.Builder)this;
         if (!var2.tls) {
            throw (Throwable)(new IllegalArgumentException("no cipher suites for cleartext connections".toString()));
         } else {
            Collection var3 = (Collection)(new ArrayList(var1.length));
            int var4 = var1.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3.add(var1[var5].javaName());
            }

            Object[] var6 = ((Collection)((List)var3)).toArray(new String[0]);
            if (var6 != null) {
               String[] var7 = (String[])var6;
               return var2.cipherSuites((String[])Arrays.copyOf(var7, var7.length));
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
         }
      }

      public final String[] getCipherSuites$okhttp() {
         return this.cipherSuites;
      }

      public final boolean getSupportsTlsExtensions$okhttp() {
         return this.supportsTlsExtensions;
      }

      public final boolean getTls$okhttp() {
         return this.tls;
      }

      public final String[] getTlsVersions$okhttp() {
         return this.tlsVersions;
      }

      public final void setCipherSuites$okhttp(String[] var1) {
         this.cipherSuites = var1;
      }

      public final void setSupportsTlsExtensions$okhttp(boolean var1) {
         this.supportsTlsExtensions = var1;
      }

      public final void setTls$okhttp(boolean var1) {
         this.tls = var1;
      }

      public final void setTlsVersions$okhttp(String[] var1) {
         this.tlsVersions = var1;
      }

      @Deprecated(
         message = "since OkHttp 3.13 all TLS-connections are expected to support TLS extensions.\nIn a future release setting this to true will be unnecessary and setting it to false\nwill have no effect."
      )
      public final ConnectionSpec.Builder supportsTlsExtensions(boolean var1) {
         ConnectionSpec.Builder var2 = (ConnectionSpec.Builder)this;
         if (var2.tls) {
            var2.supportsTlsExtensions = var1;
            return var2;
         } else {
            throw (Throwable)(new IllegalArgumentException("no TLS extensions for cleartext connections".toString()));
         }
      }

      public final ConnectionSpec.Builder tlsVersions(String... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersions");
         ConnectionSpec.Builder var2 = (ConnectionSpec.Builder)this;
         if (var2.tls) {
            boolean var3;
            if (var1.length == 0) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (var3 ^ true) {
               Object var4 = var1.clone();
               if (var4 != null) {
                  var2.tlsVersions = (String[])var4;
                  return var2;
               } else {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
               }
            } else {
               throw (Throwable)(new IllegalArgumentException("At least one TLS version is required".toString()));
            }
         } else {
            throw (Throwable)(new IllegalArgumentException("no TLS versions for cleartext connections".toString()));
         }
      }

      public final ConnectionSpec.Builder tlsVersions(TlsVersion... var1) {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersions");
         ConnectionSpec.Builder var2 = (ConnectionSpec.Builder)this;
         if (!var2.tls) {
            throw (Throwable)(new IllegalArgumentException("no TLS versions for cleartext connections".toString()));
         } else {
            Collection var3 = (Collection)(new ArrayList(var1.length));
            int var4 = var1.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               var3.add(var1[var5].javaName());
            }

            Object[] var6 = ((Collection)((List)var3)).toArray(new String[0]);
            if (var6 != null) {
               String[] var7 = (String[])var6;
               return var2.tlsVersions((String[])Arrays.copyOf(var7, var7.length));
            } else {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            }
         }
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\f\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
      d2 = {"Lokhttp3/ConnectionSpec$Companion;", "", "()V", "APPROVED_CIPHER_SUITES", "", "Lokhttp3/CipherSuite;", "[Lokhttp3/CipherSuite;", "CLEARTEXT", "Lokhttp3/ConnectionSpec;", "COMPATIBLE_TLS", "MODERN_TLS", "RESTRICTED_CIPHER_SUITES", "RESTRICTED_TLS", "okhttp"},
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
   }
}
