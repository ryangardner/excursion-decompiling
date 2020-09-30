package okhttp3;

import java.io.IOException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 &2\u00020\u0001:\u0001&B9\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0012\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n¢\u0006\u0002\u0010\u000bJ\r\u0010\u0004\u001a\u00020\u0005H\u0007¢\u0006\u0002\b\u001aJ\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0013\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b J\u000f\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b!J\u0013\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0007¢\u0006\u0002\b\"J\u000f\u0010\u0014\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b#J\r\u0010\u0002\u001a\u00020\u0003H\u0007¢\u0006\u0002\b$J\b\u0010%\u001a\u00020\u0017H\u0016R\u0013\u0010\u0004\u001a\u00020\u00058\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\fR\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\rR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f8G¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010R!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u00078GX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u000f8G¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0015R\u0018\u0010\u0016\u001a\u00020\u0017*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"},
   d2 = {"Lokhttp3/Handshake;", "", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "localCertificates", "", "Ljava/security/cert/Certificate;", "peerCertificatesFn", "Lkotlin/Function0;", "(Lokhttp3/TlsVersion;Lokhttp3/CipherSuite;Ljava/util/List;Lkotlin/jvm/functions/Function0;)V", "()Lokhttp3/CipherSuite;", "()Ljava/util/List;", "localPrincipal", "Ljava/security/Principal;", "()Ljava/security/Principal;", "peerCertificates", "peerCertificates$delegate", "Lkotlin/Lazy;", "peerPrincipal", "()Lokhttp3/TlsVersion;", "name", "", "getName", "(Ljava/security/cert/Certificate;)Ljava/lang/String;", "-deprecated_cipherSuite", "equals", "", "other", "hashCode", "", "-deprecated_localCertificates", "-deprecated_localPrincipal", "-deprecated_peerCertificates", "-deprecated_peerPrincipal", "-deprecated_tlsVersion", "toString", "Companion", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Handshake {
   public static final Handshake.Companion Companion = new Handshake.Companion((DefaultConstructorMarker)null);
   private final CipherSuite cipherSuite;
   private final List<Certificate> localCertificates;
   private final Lazy peerCertificates$delegate;
   private final TlsVersion tlsVersion;

   public Handshake(TlsVersion var1, CipherSuite var2, List<? extends Certificate> var3, final Function0<? extends List<? extends Certificate>> var4) {
      Intrinsics.checkParameterIsNotNull(var1, "tlsVersion");
      Intrinsics.checkParameterIsNotNull(var2, "cipherSuite");
      Intrinsics.checkParameterIsNotNull(var3, "localCertificates");
      Intrinsics.checkParameterIsNotNull(var4, "peerCertificatesFn");
      super();
      this.tlsVersion = var1;
      this.cipherSuite = var2;
      this.localCertificates = var3;
      this.peerCertificates$delegate = LazyKt.lazy((Function0)(new Function0<List<? extends Certificate>>() {
         public final List<Certificate> invoke() {
            List var1;
            try {
               var1 = (List)var4.invoke();
            } catch (SSLPeerUnverifiedException var2) {
               var1 = CollectionsKt.emptyList();
            }

            return var1;
         }
      }));
   }

   @JvmStatic
   public static final Handshake get(SSLSession var0) throws IOException {
      return Companion.get(var0);
   }

   @JvmStatic
   public static final Handshake get(TlsVersion var0, CipherSuite var1, List<? extends Certificate> var2, List<? extends Certificate> var3) {
      return Companion.get(var0, var1, var2, var3);
   }

   private final String getName(Certificate var1) {
      String var2;
      if (var1 instanceof X509Certificate) {
         var2 = ((X509Certificate)var1).getSubjectDN().toString();
      } else {
         var2 = var1.getType();
         Intrinsics.checkExpressionValueIsNotNull(var2, "type");
      }

      return var2;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "cipherSuite",
   imports = {}
)
   )
   public final CipherSuite _deprecated_cipherSuite/* $FF was: -deprecated_cipherSuite*/() {
      return this.cipherSuite;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "localCertificates",
   imports = {}
)
   )
   public final List<Certificate> _deprecated_localCertificates/* $FF was: -deprecated_localCertificates*/() {
      return this.localCertificates;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "localPrincipal",
   imports = {}
)
   )
   public final Principal _deprecated_localPrincipal/* $FF was: -deprecated_localPrincipal*/() {
      return this.localPrincipal();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "peerCertificates",
   imports = {}
)
   )
   public final List<Certificate> _deprecated_peerCertificates/* $FF was: -deprecated_peerCertificates*/() {
      return this.peerCertificates();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "peerPrincipal",
   imports = {}
)
   )
   public final Principal _deprecated_peerPrincipal/* $FF was: -deprecated_peerPrincipal*/() {
      return this.peerPrincipal();
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "tlsVersion",
   imports = {}
)
   )
   public final TlsVersion _deprecated_tlsVersion/* $FF was: -deprecated_tlsVersion*/() {
      return this.tlsVersion;
   }

   public final CipherSuite cipherSuite() {
      return this.cipherSuite;
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof Handshake) {
         Handshake var3 = (Handshake)var1;
         if (var3.tlsVersion == this.tlsVersion && Intrinsics.areEqual((Object)var3.cipherSuite, (Object)this.cipherSuite) && Intrinsics.areEqual((Object)var3.peerCertificates(), (Object)this.peerCertificates()) && Intrinsics.areEqual((Object)var3.localCertificates, (Object)this.localCertificates)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public int hashCode() {
      return (((527 + this.tlsVersion.hashCode()) * 31 + this.cipherSuite.hashCode()) * 31 + this.peerCertificates().hashCode()) * 31 + this.localCertificates.hashCode();
   }

   public final List<Certificate> localCertificates() {
      return this.localCertificates;
   }

   public final Principal localPrincipal() {
      Object var1 = CollectionsKt.firstOrNull(this.localCertificates);
      boolean var2 = var1 instanceof X509Certificate;
      Object var3 = null;
      if (!var2) {
         var1 = null;
      }

      X509Certificate var4 = (X509Certificate)var1;
      X500Principal var5 = (X500Principal)var3;
      if (var4 != null) {
         var5 = var4.getSubjectX500Principal();
      }

      return (Principal)var5;
   }

   public final List<Certificate> peerCertificates() {
      return (List)this.peerCertificates$delegate.getValue();
   }

   public final Principal peerPrincipal() {
      Object var1 = CollectionsKt.firstOrNull(this.peerCertificates());
      boolean var2 = var1 instanceof X509Certificate;
      Object var3 = null;
      if (!var2) {
         var1 = null;
      }

      X509Certificate var4 = (X509Certificate)var1;
      X500Principal var5 = (X500Principal)var3;
      if (var4 != null) {
         var5 = var4.getSubjectX500Principal();
      }

      return (Principal)var5;
   }

   public final TlsVersion tlsVersion() {
      return this.tlsVersion;
   }

   public String toString() {
      Iterable var1 = (Iterable)this.peerCertificates();
      Collection var2 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var1, 10)));
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         var2.add(this.getName((Certificate)var4.next()));
      }

      String var5 = ((List)var2).toString();
      StringBuilder var7 = new StringBuilder();
      var7.append("Handshake{");
      var7.append("tlsVersion=");
      var7.append(this.tlsVersion);
      var7.append(' ');
      var7.append("cipherSuite=");
      var7.append(this.cipherSuite);
      var7.append(' ');
      var7.append("peerCertificates=");
      var7.append(var5);
      var7.append(' ');
      var7.append("localCertificates=");
      Iterable var3 = (Iterable)this.localCertificates;
      Collection var6 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var3, 10)));
      Iterator var8 = var3.iterator();

      while(var8.hasNext()) {
         var6.add(this.getName((Certificate)var8.next()));
      }

      var7.append((List)var6);
      var7.append('}');
      return var7.toString();
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¢\u0006\u0002\b\u0007J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0007J\u0011\u0010\u0010\u001a\u00020\u0004*\u00020\u0006H\u0007¢\u0006\u0002\b\u0003J!\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\f\u0012\u0006\b\u0001\u0012\u00020\u000e\u0018\u00010\u0012H\u0002¢\u0006\u0002\u0010\u0013¨\u0006\u0014"},
      d2 = {"Lokhttp3/Handshake$Companion;", "", "()V", "get", "Lokhttp3/Handshake;", "sslSession", "Ljavax/net/ssl/SSLSession;", "-deprecated_get", "tlsVersion", "Lokhttp3/TlsVersion;", "cipherSuite", "Lokhttp3/CipherSuite;", "peerCertificates", "", "Ljava/security/cert/Certificate;", "localCertificates", "handshake", "toImmutableList", "", "([Ljava/security/cert/Certificate;)Ljava/util/List;", "okhttp"},
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

      private final List<Certificate> toImmutableList(Certificate[] var1) {
         List var2;
         if (var1 != null) {
            var2 = Util.immutableListOf((Certificate[])Arrays.copyOf(var1, var1.length));
         } else {
            var2 = CollectionsKt.emptyList();
         }

         return var2;
      }

      @Deprecated(
         level = DeprecationLevel.ERROR,
         message = "moved to extension function",
         replaceWith = @ReplaceWith(
   expression = "sslSession.handshake()",
   imports = {}
)
      )
      public final Handshake _deprecated_get/* $FF was: -deprecated_get*/(SSLSession var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "sslSession");
         return ((Handshake.Companion)this).get(var1);
      }

      @JvmStatic
      public final Handshake get(SSLSession var1) throws IOException {
         Intrinsics.checkParameterIsNotNull(var1, "$this$handshake");
         String var2 = var1.getCipherSuite();
         if (var2 == null) {
            throw (Throwable)(new IllegalStateException("cipherSuite == null".toString()));
         } else {
            label42: {
               int var3 = var2.hashCode();
               if (var3 != 1019404634) {
                  if (var3 == 1208658923 && var2.equals("SSL_NULL_WITH_NULL_NULL")) {
                     break label42;
                  }
               } else if (var2.equals("TLS_NULL_WITH_NULL_NULL")) {
                  break label42;
               }

               CipherSuite var4 = CipherSuite.Companion.forJavaName(var2);
               var2 = var1.getProtocol();
               if (var2 != null) {
                  if (!Intrinsics.areEqual((Object)"NONE", (Object)var2)) {
                     TlsVersion var5 = TlsVersion.Companion.forJavaName(var2);

                     final List var8;
                     try {
                        var8 = ((Handshake.Companion)this).toImmutableList(var1.getPeerCertificates());
                     } catch (SSLPeerUnverifiedException var6) {
                        var8 = CollectionsKt.emptyList();
                     }

                     return new Handshake(var5, var4, ((Handshake.Companion)this).toImmutableList(var1.getLocalCertificates()), (Function0)(new Function0<List<? extends Certificate>>() {
                        public final List<Certificate> invoke() {
                           return var8;
                        }
                     }));
                  }

                  throw (Throwable)(new IOException("tlsVersion == NONE"));
               }

               throw (Throwable)(new IllegalStateException("tlsVersion == null".toString()));
            }

            StringBuilder var7 = new StringBuilder();
            var7.append("cipherSuite == ");
            var7.append(var2);
            throw (Throwable)(new IOException(var7.toString()));
         }
      }

      @JvmStatic
      public final Handshake get(TlsVersion var1, CipherSuite var2, final List<? extends Certificate> var3, List<? extends Certificate> var4) {
         Intrinsics.checkParameterIsNotNull(var1, "tlsVersion");
         Intrinsics.checkParameterIsNotNull(var2, "cipherSuite");
         Intrinsics.checkParameterIsNotNull(var3, "peerCertificates");
         Intrinsics.checkParameterIsNotNull(var4, "localCertificates");
         var3 = Util.toImmutableList(var3);
         return new Handshake(var1, var2, Util.toImmutableList(var4), (Function0)(new Function0<List<? extends Certificate>>() {
            public final List<Certificate> invoke() {
               return var3;
            }
         }));
      }
   }
}
