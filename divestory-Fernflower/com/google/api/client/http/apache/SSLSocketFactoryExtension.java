package com.google.api.client.http.apache;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import org.apache.http.conn.ssl.SSLSocketFactory;

final class SSLSocketFactoryExtension extends SSLSocketFactory {
   private final javax.net.ssl.SSLSocketFactory socketFactory;

   SSLSocketFactoryExtension(SSLContext var1) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
      super((KeyStore)null);
      this.socketFactory = var1.getSocketFactory();
   }

   public Socket createSocket() throws IOException {
      return this.socketFactory.createSocket();
   }

   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      SSLSocket var5 = (SSLSocket)this.socketFactory.createSocket(var1, var2, var3, var4);
      this.getHostnameVerifier().verify(var2, var5);
      return var5;
   }
}
