package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class SSLSocketFactory implements LayeredSchemeSocketFactory, LayeredSocketFactory {
   public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
   public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
   public static final String SSL = "SSL";
   public static final String SSLV2 = "SSLv2";
   public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
   public static final String TLS = "TLS";
   private volatile X509HostnameVerifier hostnameVerifier;
   private final HostNameResolver nameResolver;
   private final javax.net.ssl.SSLSocketFactory socketfactory;

   private SSLSocketFactory() {
      this(createDefaultSSLContext());
   }

   @Deprecated
   public SSLSocketFactory(String var1, KeyStore var2, String var3, KeyStore var4, SecureRandom var5, HostNameResolver var6) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this(createSSLContext(var1, var2, var3, var4, var5, (TrustStrategy)null), var6);
   }

   public SSLSocketFactory(String var1, KeyStore var2, String var3, KeyStore var4, SecureRandom var5, TrustStrategy var6, X509HostnameVerifier var7) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this(createSSLContext(var1, var2, var3, var4, var5, var6), var7);
   }

   public SSLSocketFactory(String var1, KeyStore var2, String var3, KeyStore var4, SecureRandom var5, X509HostnameVerifier var6) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this(createSSLContext(var1, var2, var3, var4, var5, (TrustStrategy)null), var6);
   }

   public SSLSocketFactory(KeyStore var1) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this("TLS", (KeyStore)null, (String)null, var1, (SecureRandom)null, (TrustStrategy)null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
   }

   public SSLSocketFactory(KeyStore var1, String var2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this("TLS", var1, var2, (KeyStore)null, (SecureRandom)null, (TrustStrategy)null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
   }

   public SSLSocketFactory(KeyStore var1, String var2, KeyStore var3) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this("TLS", var1, var2, var3, (SecureRandom)null, (TrustStrategy)null, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
   }

   public SSLSocketFactory(SSLContext var1) {
      this(var1, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
   }

   @Deprecated
   public SSLSocketFactory(SSLContext var1, HostNameResolver var2) {
      this.socketfactory = var1.getSocketFactory();
      this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
      this.nameResolver = var2;
   }

   public SSLSocketFactory(SSLContext var1, X509HostnameVerifier var2) {
      this.socketfactory = var1.getSocketFactory();
      this.hostnameVerifier = var2;
      this.nameResolver = null;
   }

   public SSLSocketFactory(TrustStrategy var1) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this("TLS", (KeyStore)null, (String)null, (KeyStore)null, (SecureRandom)null, var1, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
   }

   public SSLSocketFactory(TrustStrategy var1, X509HostnameVerifier var2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
      this("TLS", (KeyStore)null, (String)null, (KeyStore)null, (SecureRandom)null, var1, var2);
   }

   private static SSLContext createDefaultSSLContext() {
      try {
         SSLContext var0 = createSSLContext("TLS", (KeyStore)null, (String)null, (KeyStore)null, (SecureRandom)null, (TrustStrategy)null);
         return var0;
      } catch (Exception var1) {
         throw new IllegalStateException("Failure initializing default SSL context", var1);
      }
   }

   private static SSLContext createSSLContext(String var0, KeyStore var1, String var2, KeyStore var3, SecureRandom var4, TrustStrategy var5) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
      String var6 = var0;
      if (var0 == null) {
         var6 = "TLS";
      }

      KeyManagerFactory var7 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
      char[] var9;
      if (var2 != null) {
         var9 = var2.toCharArray();
      } else {
         var9 = null;
      }

      var7.init(var1, var9);
      KeyManager[] var10 = var7.getKeyManagers();
      TrustManagerFactory var11 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      var11.init(var3);
      TrustManager[] var12 = var11.getTrustManagers();
      if (var12 != null && var5 != null) {
         for(int var8 = 0; var8 < var12.length; ++var8) {
            TrustManager var13 = var12[var8];
            if (var13 instanceof X509TrustManager) {
               var12[var8] = new TrustManagerDecorator((X509TrustManager)var13, var5);
            }
         }
      }

      SSLContext var14 = SSLContext.getInstance(var6);
      var14.init(var10, var12, var4);
      return var14;
   }

   public static SSLSocketFactory getSocketFactory() {
      return new SSLSocketFactory();
   }

   @Deprecated
   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException, UnknownHostException, ConnectTimeoutException {
      InetSocketAddress var10;
      if (var4 == null && var5 <= 0) {
         var10 = null;
      } else {
         int var7 = var5;
         if (var5 < 0) {
            var7 = 0;
         }

         var10 = new InetSocketAddress(var4, var7);
      }

      HostNameResolver var8 = this.nameResolver;
      InetAddress var9;
      if (var8 != null) {
         var9 = var8.resolve(var2);
      } else {
         var9 = InetAddress.getByName(var2);
      }

      return this.connectSocket(var1, new InetSocketAddress(var9, var3), var10, var6);
   }

   public Socket connectSocket(Socket var1, InetSocketAddress var2, InetSocketAddress var3, HttpParams var4) throws IOException, UnknownHostException, ConnectTimeoutException {
      if (var2 != null) {
         if (var4 != null) {
            if (var1 == null) {
               var1 = new Socket();
            }

            if (var3 != null) {
               var1.setReuseAddress(HttpConnectionParams.getSoReuseaddr(var4));
               var1.bind(var3);
            }

            int var5 = HttpConnectionParams.getConnectionTimeout(var4);
            int var6 = HttpConnectionParams.getSoTimeout(var4);

            try {
               var1.setSoTimeout(var6);
               var1.connect(var2, var5);
            } catch (SocketTimeoutException var9) {
               StringBuilder var10 = new StringBuilder();
               var10.append("Connect to ");
               var10.append(var2);
               var10.append(" timed out");
               throw new ConnectTimeoutException(var10.toString());
            }

            String var14 = var2.toString();
            var6 = var2.getPort();
            StringBuilder var11 = new StringBuilder();
            var11.append(":");
            var11.append(var6);
            String var15 = var11.toString();
            String var12 = var14;
            if (var14.endsWith(var15)) {
               var12 = var14.substring(0, var14.length() - var15.length());
            }

            SSLSocket var13;
            if (var1 instanceof SSLSocket) {
               var13 = (SSLSocket)var1;
            } else {
               var13 = (SSLSocket)this.socketfactory.createSocket(var1, var12, var6, true);
            }

            if (this.hostnameVerifier != null) {
               try {
                  this.hostnameVerifier.verify(var12, var13);
               } catch (IOException var8) {
                  try {
                     var13.close();
                  } catch (Exception var7) {
                  }

                  throw var8;
               }
            }

            return var13;
         } else {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         }
      } else {
         throw new IllegalArgumentException("Remote address may not be null");
      }
   }

   public Socket createLayeredSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      SSLSocket var5 = (SSLSocket)this.socketfactory.createSocket(var1, var2, var3, var4);
      if (this.hostnameVerifier != null) {
         this.hostnameVerifier.verify(var2, var5);
      }

      return var5;
   }

   @Deprecated
   public Socket createSocket() throws IOException {
      return this.socketfactory.createSocket();
   }

   @Deprecated
   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      return this.createLayeredSocket(var1, var2, var3, var4);
   }

   public Socket createSocket(HttpParams var1) throws IOException {
      return this.socketfactory.createSocket();
   }

   public X509HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public boolean isSecure(Socket var1) throws IllegalArgumentException {
      if (var1 != null) {
         if (var1 instanceof SSLSocket) {
            if (!var1.isClosed()) {
               return true;
            } else {
               throw new IllegalArgumentException("Socket is closed");
            }
         } else {
            throw new IllegalArgumentException("Socket not created by this factory");
         }
      } else {
         throw new IllegalArgumentException("Socket may not be null");
      }
   }

   @Deprecated
   public void setHostnameVerifier(X509HostnameVerifier var1) {
      if (var1 != null) {
         this.hostnameVerifier = var1;
      } else {
         throw new IllegalArgumentException("Hostname verifier may not be null");
      }
   }
}
