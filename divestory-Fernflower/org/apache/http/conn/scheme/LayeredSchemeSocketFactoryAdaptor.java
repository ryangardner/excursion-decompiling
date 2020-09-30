package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@Deprecated
class LayeredSchemeSocketFactoryAdaptor extends SchemeSocketFactoryAdaptor implements LayeredSchemeSocketFactory {
   private final LayeredSocketFactory factory;

   LayeredSchemeSocketFactoryAdaptor(LayeredSocketFactory var1) {
      super(var1);
      this.factory = var1;
   }

   public Socket createLayeredSocket(Socket var1, String var2, int var3, boolean var4) throws IOException, UnknownHostException {
      return this.factory.createSocket(var1, var2, var3, var4);
   }
}
