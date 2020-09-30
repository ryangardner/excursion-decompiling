package com.sun.mail.iap;

public class ConnectionException extends ProtocolException {
   private static final long serialVersionUID = 5749739604257464727L;
   private transient Protocol p;

   public ConnectionException() {
   }

   public ConnectionException(Protocol var1, Response var2) {
      super(var2);
      this.p = var1;
   }

   public ConnectionException(String var1) {
      super(var1);
   }

   public Protocol getProtocol() {
      return this.p;
   }
}
