package com.sun.mail.iap;

public class CommandFailedException extends ProtocolException {
   private static final long serialVersionUID = 793932807880443631L;

   public CommandFailedException() {
   }

   public CommandFailedException(Response var1) {
      super(var1);
   }

   public CommandFailedException(String var1) {
      super(var1);
   }
}
