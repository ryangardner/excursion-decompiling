package com.sun.mail.iap;

public class LiteralException extends ProtocolException {
   private static final long serialVersionUID = -6919179828339609913L;

   public LiteralException(Response var1) {
      super(var1.toString());
      this.response = var1;
   }
}
