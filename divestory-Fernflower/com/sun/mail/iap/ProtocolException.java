package com.sun.mail.iap;

public class ProtocolException extends Exception {
   private static final long serialVersionUID = -4360500807971797439L;
   protected transient Response response = null;

   public ProtocolException() {
   }

   public ProtocolException(Response var1) {
      super(var1.toString());
      this.response = var1;
   }

   public ProtocolException(String var1) {
      super(var1);
   }

   public Response getResponse() {
      return this.response;
   }
}
