package com.sun.mail.iap;

public class BadCommandException extends ProtocolException {
   private static final long serialVersionUID = 5769722539397237515L;

   public BadCommandException() {
   }

   public BadCommandException(Response var1) {
      super(var1);
   }

   public BadCommandException(String var1) {
      super(var1);
   }
}
