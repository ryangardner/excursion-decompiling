package okhttp3;

import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \"2\u00020\u0001:\u0003!\"#B!\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J)\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u0011H\u0000¢\u0006\u0002\b\u0014J)\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00170\u0016\"\u00020\u0017H\u0007¢\u0006\u0002\u0010\u0018J\u001c\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0012J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u00122\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0015\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\b R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006$"},
   d2 = {"Lokhttp3/CertificatePinner;", "", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "(Ljava/util/Set;Lokhttp3/internal/tls/CertificateChainCleaner;)V", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "getPins", "()Ljava/util/Set;", "check", "", "hostname", "", "cleanedPeerCertificatesFn", "Lkotlin/Function0;", "", "Ljava/security/cert/X509Certificate;", "check$okhttp", "peerCertificates", "", "Ljava/security/cert/Certificate;", "(Ljava/lang/String;[Ljava/security/cert/Certificate;)V", "equals", "", "other", "findMatchingPins", "hashCode", "", "withCertificateChainCleaner", "withCertificateChainCleaner$okhttp", "Builder", "Companion", "Pin", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class CertificatePinner {
   public static final CertificatePinner.Companion Companion = new CertificatePinner.Companion((DefaultConstructorMarker)null);
   public static final CertificatePinner DEFAULT = (new CertificatePinner.Builder()).build();
   private final CertificateChainCleaner certificateChainCleaner;
   private final Set<CertificatePinner.Pin> pins;

   public CertificatePinner(Set<CertificatePinner.Pin> var1, CertificateChainCleaner var2) {
      Intrinsics.checkParameterIsNotNull(var1, "pins");
      super();
      this.pins = var1;
      this.certificateChainCleaner = var2;
   }

   // $FF: synthetic method
   public CertificatePinner(Set var1, CertificateChainCleaner var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = (CertificateChainCleaner)null;
      }

      this(var1, var2);
   }

   @JvmStatic
   public static final String pin(Certificate var0) {
      return Companion.pin(var0);
   }

   @JvmStatic
   public static final ByteString sha1Hash(X509Certificate var0) {
      return Companion.sha1Hash(var0);
   }

   @JvmStatic
   public static final ByteString sha256Hash(X509Certificate var0) {
      return Companion.sha256Hash(var0);
   }

   public final void check(final String var1, final List<? extends Certificate> var2) throws SSLPeerUnverifiedException {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "peerCertificates");
      this.check$okhttp(var1, (Function0)(new Function0<List<? extends X509Certificate>>() {
         public final List<X509Certificate> invoke() {
            List var4;
            label23: {
               CertificateChainCleaner var1x = CertificatePinner.this.getCertificateChainCleaner$okhttp();
               if (var1x != null) {
                  var4 = var1x.clean(var2, var1);
                  if (var4 != null) {
                     break label23;
                  }
               }

               var4 = var2;
            }

            Iterable var2x = (Iterable)var4;
            Collection var5 = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(var2x, 10)));
            Iterator var3 = var2x.iterator();

            while(var3.hasNext()) {
               Certificate var6 = (Certificate)var3.next();
               if (var6 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
               }

               var5.add((X509Certificate)var6);
            }

            return (List)var5;
         }
      }));
   }

   @Deprecated(
      message = "replaced with {@link #check(String, List)}.",
      replaceWith = @ReplaceWith(
   expression = "check(hostname, peerCertificates.toList())",
   imports = {}
)
   )
   public final void check(String var1, Certificate... var2) throws SSLPeerUnverifiedException {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "peerCertificates");
      this.check(var1, ArraysKt.toList(var2));
   }

   public final void check$okhttp(String var1, Function0<? extends List<? extends X509Certificate>> var2) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Intrinsics.checkParameterIsNotNull(var2, "cleanedPeerCertificatesFn");
      List var3 = this.findMatchingPins(var1);
      if (!var3.isEmpty()) {
         List var4 = (List)var2.invoke();
         Iterator var5 = var4.iterator();

         CertificatePinner.Pin var9;
         label64:
         while(true) {
            if (!var5.hasNext()) {
               StringBuilder var15 = new StringBuilder();
               var15.append("Certificate pinning failure!");
               var15.append("\n  Peer certificate chain:");
               Iterator var16 = var4.iterator();

               while(var16.hasNext()) {
                  X509Certificate var19 = (X509Certificate)var16.next();
                  var15.append("\n    ");
                  var15.append(Companion.pin((Certificate)var19));
                  var15.append(": ");
                  Principal var20 = var19.getSubjectDN();
                  Intrinsics.checkExpressionValueIsNotNull(var20, "element.subjectDN");
                  var15.append(var20.getName());
               }

               var15.append("\n  Pinned certificates for ");
               var15.append(var1);
               var15.append(":");
               Iterator var13 = var3.iterator();

               while(var13.hasNext()) {
                  CertificatePinner.Pin var17 = (CertificatePinner.Pin)var13.next();
                  var15.append("\n    ");
                  var15.append(var17);
               }

               var1 = var15.toString();
               Intrinsics.checkExpressionValueIsNotNull(var1, "StringBuilder().apply(builderAction).toString()");
               throw (Throwable)(new SSLPeerUnverifiedException(var1));
            }

            X509Certificate var6 = (X509Certificate)var5.next();
            ByteString var7 = (ByteString)null;
            Iterator var8 = var3.iterator();
            ByteString var14 = var7;

            while(var8.hasNext()) {
               var9 = (CertificatePinner.Pin)var8.next();
               String var10 = var9.getHashAlgorithm();
               int var11 = var10.hashCode();
               ByteString var18;
               if (var11 != -903629273) {
                  if (var11 != 3528965 || !var10.equals("sha1")) {
                     break label64;
                  }

                  var18 = var14;
                  if (var14 == null) {
                     var18 = Companion.sha1Hash(var6);
                  }

                  var14 = var18;
                  if (Intrinsics.areEqual((Object)var9.getHash(), (Object)var18)) {
                     return;
                  }
               } else {
                  if (!var10.equals("sha256")) {
                     break label64;
                  }

                  var18 = var7;
                  if (var7 == null) {
                     var18 = Companion.sha256Hash(var6);
                  }

                  var7 = var18;
                  if (Intrinsics.areEqual((Object)var9.getHash(), (Object)var18)) {
                     return;
                  }
               }
            }
         }

         StringBuilder var12 = new StringBuilder();
         var12.append("unsupported hashAlgorithm: ");
         var12.append(var9.getHashAlgorithm());
         throw (Throwable)(new AssertionError(var12.toString()));
      }
   }

   public boolean equals(Object var1) {
      boolean var2;
      if (var1 instanceof CertificatePinner) {
         CertificatePinner var3 = (CertificatePinner)var1;
         if (Intrinsics.areEqual((Object)var3.pins, (Object)this.pins) && Intrinsics.areEqual((Object)var3.certificateChainCleaner, (Object)this.certificateChainCleaner)) {
            var2 = true;
            return var2;
         }
      }

      var2 = false;
      return var2;
   }

   public final List<CertificatePinner.Pin> findMatchingPins(String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "hostname");
      Iterable var2 = (Iterable)this.pins;
      List var3 = CollectionsKt.emptyList();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         Object var5 = var4.next();
         if (((CertificatePinner.Pin)var5).matchesHostname(var1)) {
            List var6 = var3;
            if (var3.isEmpty()) {
               var6 = (List)(new ArrayList());
            }

            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<T>");
            }

            TypeIntrinsics.asMutableList(var6).add(var5);
            var3 = var6;
         }
      }

      return var3;
   }

   public final CertificateChainCleaner getCertificateChainCleaner$okhttp() {
      return this.certificateChainCleaner;
   }

   public final Set<CertificatePinner.Pin> getPins() {
      return this.pins;
   }

   public int hashCode() {
      int var1 = this.pins.hashCode();
      CertificateChainCleaner var2 = this.certificateChainCleaner;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         var3 = 0;
      }

      return (1517 + var1) * 41 + var3;
   }

   public final CertificatePinner withCertificateChainCleaner$okhttp(CertificateChainCleaner var1) {
      Intrinsics.checkParameterIsNotNull(var1, "certificateChainCleaner");
      CertificatePinner var2;
      if (Intrinsics.areEqual((Object)this.certificateChainCleaner, (Object)var1)) {
         var2 = this;
      } else {
         var2 = new CertificatePinner(this.pins, var1);
      }

      return var2;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u000b\"\u00020\n¢\u0006\u0002\u0010\fJ\u0006\u0010\r\u001a\u00020\u000eR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000f"},
      d2 = {"Lokhttp3/CertificatePinner$Builder;", "", "()V", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "getPins", "()Ljava/util/List;", "add", "pattern", "", "", "(Ljava/lang/String;[Ljava/lang/String;)Lokhttp3/CertificatePinner$Builder;", "build", "Lokhttp3/CertificatePinner;", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Builder {
      private final List<CertificatePinner.Pin> pins = (List)(new ArrayList());

      public final CertificatePinner.Builder add(String var1, String... var2) {
         Intrinsics.checkParameterIsNotNull(var1, "pattern");
         Intrinsics.checkParameterIsNotNull(var2, "pins");
         CertificatePinner.Builder var3 = (CertificatePinner.Builder)this;
         int var4 = var2.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var2[var5];
            var3.pins.add(new CertificatePinner.Pin(var1, var6));
         }

         return var3;
      }

      public final CertificatePinner build() {
         return new CertificatePinner(CollectionsKt.toSet((Iterable)this.pins), (CertificateChainCleaner)null, 2, (DefaultConstructorMarker)null);
      }

      public final List<CertificatePinner.Pin> getPins() {
         return this.pins;
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\f\u0010\t\u001a\u00020\n*\u00020\u000bH\u0007J\f\u0010\f\u001a\u00020\n*\u00020\u000bH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
      d2 = {"Lokhttp3/CertificatePinner$Companion;", "", "()V", "DEFAULT", "Lokhttp3/CertificatePinner;", "pin", "", "certificate", "Ljava/security/cert/Certificate;", "sha1Hash", "Lokio/ByteString;", "Ljava/security/cert/X509Certificate;", "sha256Hash", "okhttp"},
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

      @JvmStatic
      public final String pin(Certificate var1) {
         Intrinsics.checkParameterIsNotNull(var1, "certificate");
         if (var1 instanceof X509Certificate) {
            StringBuilder var2 = new StringBuilder();
            var2.append("sha256/");
            var2.append(((CertificatePinner.Companion)this).sha256Hash((X509Certificate)var1).base64());
            return var2.toString();
         } else {
            throw (Throwable)(new IllegalArgumentException("Certificate pinning requires X509 certificates".toString()));
         }
      }

      @JvmStatic
      public final ByteString sha1Hash(X509Certificate var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$sha1Hash");
         ByteString.Companion var2 = ByteString.Companion;
         PublicKey var3 = var1.getPublicKey();
         Intrinsics.checkExpressionValueIsNotNull(var3, "publicKey");
         byte[] var4 = var3.getEncoded();
         Intrinsics.checkExpressionValueIsNotNull(var4, "publicKey.encoded");
         return ByteString.Companion.of$default(var2, var4, 0, 0, 3, (Object)null).sha1();
      }

      @JvmStatic
      public final ByteString sha256Hash(X509Certificate var1) {
         Intrinsics.checkParameterIsNotNull(var1, "$this$sha256Hash");
         ByteString.Companion var2 = ByteString.Companion;
         PublicKey var3 = var1.getPublicKey();
         Intrinsics.checkExpressionValueIsNotNull(var3, "publicKey");
         byte[] var4 = var3.getEncoded();
         Intrinsics.checkExpressionValueIsNotNull(var4, "publicKey.encoded");
         return ByteString.Companion.of$default(var2, var4, 0, 0, 3, (Object)null).sha256();
      }
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0003J\b\u0010\u0018\u001a\u00020\u0003H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u0019"},
      d2 = {"Lokhttp3/CertificatePinner$Pin;", "", "pattern", "", "pin", "(Ljava/lang/String;Ljava/lang/String;)V", "hash", "Lokio/ByteString;", "getHash", "()Lokio/ByteString;", "hashAlgorithm", "getHashAlgorithm", "()Ljava/lang/String;", "getPattern", "equals", "", "other", "hashCode", "", "matchesCertificate", "certificate", "Ljava/security/cert/X509Certificate;", "matchesHostname", "hostname", "toString", "okhttp"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Pin {
      private final ByteString hash;
      private final String hashAlgorithm;
      private final String pattern;

      public Pin(String var1, String var2) {
         Intrinsics.checkParameterIsNotNull(var1, "pattern");
         Intrinsics.checkParameterIsNotNull(var2, "pin");
         super();
         boolean var3;
         if ((!StringsKt.startsWith$default(var1, "*.", false, 2, (Object)null) || StringsKt.indexOf$default((CharSequence)var1, "*", 1, false, 4, (Object)null) != -1) && (!StringsKt.startsWith$default(var1, "**.", false, 2, (Object)null) || StringsKt.indexOf$default((CharSequence)var1, "*", 2, false, 4, (Object)null) != -1) && StringsKt.indexOf$default((CharSequence)var1, "*", 0, false, 6, (Object)null) != -1) {
            var3 = false;
         } else {
            var3 = true;
         }

         StringBuilder var8;
         if (var3) {
            String var4 = HostnamesKt.toCanonicalHost(var1);
            if (var4 != null) {
               this.pattern = var4;
               ByteString var6;
               StringBuilder var7;
               if (StringsKt.startsWith$default(var2, "sha1/", false, 2, (Object)null)) {
                  this.hashAlgorithm = "sha1";
                  ByteString.Companion var5 = ByteString.Companion;
                  var4 = var2.substring(5);
                  Intrinsics.checkExpressionValueIsNotNull(var4, "(this as java.lang.String).substring(startIndex)");
                  var6 = var5.decodeBase64(var4);
                  if (var6 == null) {
                     var7 = new StringBuilder();
                     var7.append("Invalid pin hash: ");
                     var7.append(var2);
                     throw (Throwable)(new IllegalArgumentException(var7.toString()));
                  }

                  this.hash = var6;
               } else {
                  if (!StringsKt.startsWith$default(var2, "sha256/", false, 2, (Object)null)) {
                     var7 = new StringBuilder();
                     var7.append("pins must start with 'sha256/' or 'sha1/': ");
                     var7.append(var2);
                     throw (Throwable)(new IllegalArgumentException(var7.toString()));
                  }

                  this.hashAlgorithm = "sha256";
                  ByteString.Companion var9 = ByteString.Companion;
                  var1 = var2.substring(7);
                  Intrinsics.checkExpressionValueIsNotNull(var1, "(this as java.lang.String).substring(startIndex)");
                  var6 = var9.decodeBase64(var1);
                  if (var6 == null) {
                     var7 = new StringBuilder();
                     var7.append("Invalid pin hash: ");
                     var7.append(var2);
                     throw (Throwable)(new IllegalArgumentException(var7.toString()));
                  }

                  this.hash = var6;
               }

            } else {
               var8 = new StringBuilder();
               var8.append("Invalid pattern: ");
               var8.append(var1);
               throw (Throwable)(new IllegalArgumentException(var8.toString()));
            }
         } else {
            var8 = new StringBuilder();
            var8.append("Unexpected pattern: ");
            var8.append(var1);
            throw (Throwable)(new IllegalArgumentException(var8.toString().toString()));
         }
      }

      public boolean equals(Object var1) {
         if ((CertificatePinner.Pin)this == var1) {
            return true;
         } else if (!(var1 instanceof CertificatePinner.Pin)) {
            return false;
         } else {
            String var2 = this.pattern;
            CertificatePinner.Pin var3 = (CertificatePinner.Pin)var1;
            if (Intrinsics.areEqual((Object)var2, (Object)var3.pattern) ^ true) {
               return false;
            } else if (Intrinsics.areEqual((Object)this.hashAlgorithm, (Object)var3.hashAlgorithm) ^ true) {
               return false;
            } else {
               return !(Intrinsics.areEqual((Object)this.hash, (Object)var3.hash) ^ true);
            }
         }
      }

      public final ByteString getHash() {
         return this.hash;
      }

      public final String getHashAlgorithm() {
         return this.hashAlgorithm;
      }

      public final String getPattern() {
         return this.pattern;
      }

      public int hashCode() {
         return (this.pattern.hashCode() * 31 + this.hashAlgorithm.hashCode()) * 31 + this.hash.hashCode();
      }

      public final boolean matchesCertificate(X509Certificate var1) {
         Intrinsics.checkParameterIsNotNull(var1, "certificate");
         String var2 = this.hashAlgorithm;
         int var3 = var2.hashCode();
         boolean var4;
         if (var3 != -903629273) {
            if (var3 == 3528965 && var2.equals("sha1")) {
               var4 = Intrinsics.areEqual((Object)this.hash, (Object)CertificatePinner.Companion.sha1Hash(var1));
               return var4;
            }
         } else if (var2.equals("sha256")) {
            var4 = Intrinsics.areEqual((Object)this.hash, (Object)CertificatePinner.Companion.sha256Hash(var1));
            return var4;
         }

         var4 = false;
         return var4;
      }

      public final boolean matchesHostname(String var1) {
         Intrinsics.checkParameterIsNotNull(var1, "hostname");
         String var2 = this.pattern;
         boolean var3 = false;
         int var4;
         int var5;
         boolean var6;
         if (StringsKt.startsWith$default(var2, "**.", false, 2, (Object)null)) {
            var4 = this.pattern.length() - 3;
            var5 = var1.length() - var4;
            var6 = var3;
            if (!StringsKt.regionMatches$default(var1, var1.length() - var4, this.pattern, 3, var4, false, 16, (Object)null)) {
               return var6;
            }

            if (var5 != 0) {
               var6 = var3;
               if (var1.charAt(var5 - 1) != '.') {
                  return var6;
               }
            }
         } else {
            if (!StringsKt.startsWith$default(this.pattern, "*.", false, 2, (Object)null)) {
               var6 = Intrinsics.areEqual((Object)var1, (Object)this.pattern);
               return var6;
            }

            var4 = this.pattern.length() - 1;
            var5 = var1.length();
            var6 = var3;
            if (!StringsKt.regionMatches$default(var1, var1.length() - var4, this.pattern, 1, var4, false, 16, (Object)null)) {
               return var6;
            }

            var6 = var3;
            if (StringsKt.lastIndexOf$default((CharSequence)var1, '.', var5 - var4 - 1, false, 4, (Object)null) != -1) {
               return var6;
            }
         }

         var6 = true;
         return var6;
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append(this.hashAlgorithm);
         var1.append('/');
         var1.append(this.hash.base64());
         return var1.toString();
      }
   }
}
