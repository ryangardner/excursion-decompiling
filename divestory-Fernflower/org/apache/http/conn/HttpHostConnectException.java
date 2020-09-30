package org.apache.http.conn;

import java.net.ConnectException;
import org.apache.http.HttpHost;

public class HttpHostConnectException extends ConnectException {
   private static final long serialVersionUID = -3194482710275220224L;
   private final HttpHost host;

   public HttpHostConnectException(HttpHost var1, ConnectException var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("Connection to ");
      var3.append(var1);
      var3.append(" refused");
      super(var3.toString());
      this.host = var1;
      this.initCause(var2);
   }

   public HttpHost getHost() {
      return this.host;
   }
}
