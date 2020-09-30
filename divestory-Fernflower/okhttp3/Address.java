package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.Objects;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000f\u0018\u00002\u00020\u0001By\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015\u0012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0015\u0012\u0006\u0010\u0019\u001a\u00020\u001a¢\u0006\u0002\u0010\u001bJ\u000f\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007¢\u0006\u0002\b(J\u0013\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0015H\u0007¢\u0006\u0002\b)J\r\u0010\u0006\u001a\u00020\u0007H\u0007¢\u0006\u0002\b*J\u0013\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0015\u0010.\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0000H\u0000¢\u0006\u0002\b0J\b\u00101\u001a\u00020\u0005H\u0016J\u000f\u0010\f\u001a\u0004\u0018\u00010\rH\u0007¢\u0006\u0002\b2J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0007¢\u0006\u0002\b3J\u000f\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007¢\u0006\u0002\b4J\r\u0010\u0010\u001a\u00020\u0011H\u0007¢\u0006\u0002\b5J\r\u0010\u0019\u001a\u00020\u001aH\u0007¢\u0006\u0002\b6J\r\u0010\b\u001a\u00020\tH\u0007¢\u0006\u0002\b7J\u000f\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0007¢\u0006\u0002\b8J\b\u00109\u001a\u00020\u0003H\u0016J\r\u0010%\u001a\u00020&H\u0007¢\u0006\u0002\b:R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001cR\u0019\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00158G¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u001dR\u0013\u0010\u0006\u001a\u00020\u00078\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u001eR\u0015\u0010\f\u001a\u0004\u0018\u00010\r8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u001fR\u0019\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00158G¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u001dR\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u00138\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010 R\u0013\u0010\u0010\u001a\u00020\u00118\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010!R\u0013\u0010\u0019\u001a\u00020\u001a8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\"R\u0013\u0010\b\u001a\u00020\t8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010#R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010$R\u0013\u0010%\u001a\u00020&8G¢\u0006\b\n\u0000\u001a\u0004\b%\u0010'¨\u0006;"},
   d2 = {"Lokhttp3/Address;", "", "uriHost", "", "uriPort", "", "dns", "Lokhttp3/Dns;", "socketFactory", "Ljavax/net/SocketFactory;", "sslSocketFactory", "Ljavax/net/ssl/SSLSocketFactory;", "hostnameVerifier", "Ljavax/net/ssl/HostnameVerifier;", "certificatePinner", "Lokhttp3/CertificatePinner;", "proxyAuthenticator", "Lokhttp3/Authenticator;", "proxy", "Ljava/net/Proxy;", "protocols", "", "Lokhttp3/Protocol;", "connectionSpecs", "Lokhttp3/ConnectionSpec;", "proxySelector", "Ljava/net/ProxySelector;", "(Ljava/lang/String;ILokhttp3/Dns;Ljavax/net/SocketFactory;Ljavax/net/ssl/SSLSocketFactory;Ljavax/net/ssl/HostnameVerifier;Lokhttp3/CertificatePinner;Lokhttp3/Authenticator;Ljava/net/Proxy;Ljava/util/List;Ljava/util/List;Ljava/net/ProxySelector;)V", "()Lokhttp3/CertificatePinner;", "()Ljava/util/List;", "()Lokhttp3/Dns;", "()Ljavax/net/ssl/HostnameVerifier;", "()Ljava/net/Proxy;", "()Lokhttp3/Authenticator;", "()Ljava/net/ProxySelector;", "()Ljavax/net/SocketFactory;", "()Ljavax/net/ssl/SSLSocketFactory;", "url", "Lokhttp3/HttpUrl;", "()Lokhttp3/HttpUrl;", "-deprecated_certificatePinner", "-deprecated_connectionSpecs", "-deprecated_dns", "equals", "", "other", "equalsNonHost", "that", "equalsNonHost$okhttp", "hashCode", "-deprecated_hostnameVerifier", "-deprecated_protocols", "-deprecated_proxy", "-deprecated_proxyAuthenticator", "-deprecated_proxySelector", "-deprecated_socketFactory", "-deprecated_sslSocketFactory", "toString", "-deprecated_url", "okhttp"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Address {
   private final CertificatePinner certificatePinner;
   private final List<ConnectionSpec> connectionSpecs;
   private final Dns dns;
   private final HostnameVerifier hostnameVerifier;
   private final List<Protocol> protocols;
   private final Proxy proxy;
   private final Authenticator proxyAuthenticator;
   private final ProxySelector proxySelector;
   private final SocketFactory socketFactory;
   private final SSLSocketFactory sslSocketFactory;
   private final HttpUrl url;

   public Address(String var1, int var2, Dns var3, SocketFactory var4, SSLSocketFactory var5, HostnameVerifier var6, CertificatePinner var7, Authenticator var8, Proxy var9, List<? extends Protocol> var10, List<ConnectionSpec> var11, ProxySelector var12) {
      Intrinsics.checkParameterIsNotNull(var1, "uriHost");
      Intrinsics.checkParameterIsNotNull(var3, "dns");
      Intrinsics.checkParameterIsNotNull(var4, "socketFactory");
      Intrinsics.checkParameterIsNotNull(var8, "proxyAuthenticator");
      Intrinsics.checkParameterIsNotNull(var10, "protocols");
      Intrinsics.checkParameterIsNotNull(var11, "connectionSpecs");
      Intrinsics.checkParameterIsNotNull(var12, "proxySelector");
      super();
      this.dns = var3;
      this.socketFactory = var4;
      this.sslSocketFactory = var5;
      this.hostnameVerifier = var6;
      this.certificatePinner = var7;
      this.proxyAuthenticator = var8;
      this.proxy = var9;
      this.proxySelector = var12;
      HttpUrl.Builder var14 = new HttpUrl.Builder();
      String var13;
      if (this.sslSocketFactory != null) {
         var13 = "https";
      } else {
         var13 = "http";
      }

      this.url = var14.scheme(var13).host(var1).port(var2).build();
      this.protocols = Util.toImmutableList(var10);
      this.connectionSpecs = Util.toImmutableList(var11);
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "certificatePinner",
   imports = {}
)
   )
   public final CertificatePinner _deprecated_certificatePinner/* $FF was: -deprecated_certificatePinner*/() {
      return this.certificatePinner;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "connectionSpecs",
   imports = {}
)
   )
   public final List<ConnectionSpec> _deprecated_connectionSpecs/* $FF was: -deprecated_connectionSpecs*/() {
      return this.connectionSpecs;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "dns",
   imports = {}
)
   )
   public final Dns _deprecated_dns/* $FF was: -deprecated_dns*/() {
      return this.dns;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "hostnameVerifier",
   imports = {}
)
   )
   public final HostnameVerifier _deprecated_hostnameVerifier/* $FF was: -deprecated_hostnameVerifier*/() {
      return this.hostnameVerifier;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "protocols",
   imports = {}
)
   )
   public final List<Protocol> _deprecated_protocols/* $FF was: -deprecated_protocols*/() {
      return this.protocols;
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
   expression = "proxyAuthenticator",
   imports = {}
)
   )
   public final Authenticator _deprecated_proxyAuthenticator/* $FF was: -deprecated_proxyAuthenticator*/() {
      return this.proxyAuthenticator;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "proxySelector",
   imports = {}
)
   )
   public final ProxySelector _deprecated_proxySelector/* $FF was: -deprecated_proxySelector*/() {
      return this.proxySelector;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "socketFactory",
   imports = {}
)
   )
   public final SocketFactory _deprecated_socketFactory/* $FF was: -deprecated_socketFactory*/() {
      return this.socketFactory;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "sslSocketFactory",
   imports = {}
)
   )
   public final SSLSocketFactory _deprecated_sslSocketFactory/* $FF was: -deprecated_sslSocketFactory*/() {
      return this.sslSocketFactory;
   }

   @Deprecated(
      level = DeprecationLevel.ERROR,
      message = "moved to val",
      replaceWith = @ReplaceWith(
   expression = "url",
   imports = {}
)
   )
   public final HttpUrl _deprecated_url/* $FF was: -deprecated_url*/() {
      return this.url;
   }

   public final CertificatePinner certificatePinner() {
      return this.certificatePinner;
   }

   public final List<ConnectionSpec> connectionSpecs() {
      return this.connectionSpecs;
   }

   public final Dns dns() {
      return this.dns;
   }

   public boolean equals(Object var1) {
      boolean var3;
      if (var1 instanceof Address) {
         HttpUrl var2 = this.url;
         Address var4 = (Address)var1;
         if (Intrinsics.areEqual((Object)var2, (Object)var4.url) && this.equalsNonHost$okhttp(var4)) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public final boolean equalsNonHost$okhttp(Address var1) {
      Intrinsics.checkParameterIsNotNull(var1, "that");
      boolean var2;
      if (Intrinsics.areEqual((Object)this.dns, (Object)var1.dns) && Intrinsics.areEqual((Object)this.proxyAuthenticator, (Object)var1.proxyAuthenticator) && Intrinsics.areEqual((Object)this.protocols, (Object)var1.protocols) && Intrinsics.areEqual((Object)this.connectionSpecs, (Object)var1.connectionSpecs) && Intrinsics.areEqual((Object)this.proxySelector, (Object)var1.proxySelector) && Intrinsics.areEqual((Object)this.proxy, (Object)var1.proxy) && Intrinsics.areEqual((Object)this.sslSocketFactory, (Object)var1.sslSocketFactory) && Intrinsics.areEqual((Object)this.hostnameVerifier, (Object)var1.hostnameVerifier) && Intrinsics.areEqual((Object)this.certificatePinner, (Object)var1.certificatePinner) && this.url.port() == var1.url.port()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return (((((((((527 + this.url.hashCode()) * 31 + this.dns.hashCode()) * 31 + this.proxyAuthenticator.hashCode()) * 31 + this.protocols.hashCode()) * 31 + this.connectionSpecs.hashCode()) * 31 + this.proxySelector.hashCode()) * 31 + Objects.hashCode(this.proxy)) * 31 + Objects.hashCode(this.sslSocketFactory)) * 31 + Objects.hashCode(this.hostnameVerifier)) * 31 + Objects.hashCode(this.certificatePinner);
   }

   public final HostnameVerifier hostnameVerifier() {
      return this.hostnameVerifier;
   }

   public final List<Protocol> protocols() {
      return this.protocols;
   }

   public final Proxy proxy() {
      return this.proxy;
   }

   public final Authenticator proxyAuthenticator() {
      return this.proxyAuthenticator;
   }

   public final ProxySelector proxySelector() {
      return this.proxySelector;
   }

   public final SocketFactory socketFactory() {
      return this.socketFactory;
   }

   public final SSLSocketFactory sslSocketFactory() {
      return this.sslSocketFactory;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Address{");
      var1.append(this.url.host());
      var1.append(':');
      var1.append(this.url.port());
      var1.append(", ");
      StringBuilder var2;
      Object var3;
      if (this.proxy != null) {
         var2 = new StringBuilder();
         var2.append("proxy=");
         var3 = this.proxy;
      } else {
         var2 = new StringBuilder();
         var2.append("proxySelector=");
         var3 = this.proxySelector;
      }

      var2.append(var3);
      var1.append(var2.toString());
      var1.append("}");
      return var1.toString();
   }

   public final HttpUrl url() {
      return this.url;
   }
}
